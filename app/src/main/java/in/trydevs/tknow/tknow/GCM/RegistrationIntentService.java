/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package in.trydevs.tknow.tknow.GCM;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import in.trydevs.tknow.tknow.DataClasses.People;
import in.trydevs.tknow.tknow.DataClasses.Post;
import in.trydevs.tknow.tknow.DataClasses.UrlLinkNames;
import in.trydevs.tknow.tknow.Network.CustomRequest;
import in.trydevs.tknow.tknow.Network.VolleySingleton;
import in.trydevs.tknow.tknow.R;
import in.trydevs.tknow.tknow.extras.MyApplication;

import static com.android.volley.Request.Method.POST;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global", "tknow"};
    SharedPreferences sharedPreferences;

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            // In the (unlikely) event that multiple refresh operations occur simultaneously,
            // ensure that they are processed sequentially.
            synchronized (TAG) {
                // [START register_for_gcm]
                // Initially this call goes out to the network to retrieve the token, subsequent calls
                // are local.
                // [START get_token]
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                // [END get_token]
                Log.i(TAG, "GCM Registration Token: " + token);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (!sentToken)
                    sendRegistrationToServer(token);

                // Subscribe to topic channels
                subscribeTopics(token);

                // You should store a boolean that indicates whether the generated token has been
                // sent to your server. If the boolean is false, send the token to your server,
                // otherwise your server should have already received the token.
                // [END register_for_gcm]
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
        }
    }

    /**
     * Persist registration to third-party servers.
     * <p/>
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        RequestQueue requestQueue = VolleySingleton.getInstance().getmRequestQueue();
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        final CustomRequest request = new CustomRequest(POST, UrlLinkNames.getUrlGcmRegister(), params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("registration response", response.toString());
                try {
                    if (response.has(UrlLinkNames.getJsonResult()))
                        if (response.getString(UrlLinkNames.getJsonResult()).equalsIgnoreCase(UrlLinkNames.getJsonSuccess()))
                            Toast.makeText(MyApplication.getAppContext(), getResources().getString(R.string.gcm_registeration_success), Toast.LENGTH_LONG).show();
                    if (response.has(UrlLinkNames.getJsonPeople())) {
                        JSONArray jsonArray = response.getJSONArray(UrlLinkNames.getJsonPeople());
                        List<People> peoples = Collections.emptyList();
                        peoples = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject objectPeople = jsonArray.getJSONObject(i);
                            People people = People.getPeopleFromJson(objectPeople);
                            peoples.add(people);
                        }
                        MyApplication.getWritableDatabase().insertPeopleData(peoples);
                    }
                    if (response.has(UrlLinkNames.getJsonPost())) {
                        List<Post> posts = Collections.emptyList();
                        // Getting posts if we have any
                        JSONArray jsonArrayPost = new JSONArray(response.getString(UrlLinkNames.getJsonPost()));
                        posts = new ArrayList<>();
                        for (int i = 0; i < jsonArrayPost.length(); i++) {
                            JSONObject objectPost = jsonArrayPost.getJSONObject(i);
                            Post post = Post.fromJSON(objectPost);
                            posts.add(post);
                        }
                        MyApplication.getWritableDatabase().insertPostData(posts);
                    }
                    sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
                    // Notify UI that registration has completed, so the progress indicator can be hidden.
                    Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
                    LocalBroadcastManager.getInstance(RegistrationIntentService.this).sendBroadcast(registrationComplete);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("registration error", error.toString());
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        for (String topic : TOPICS) {
            GcmPubSub pubSub = GcmPubSub.getInstance(this);
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
    // [END subscribe_topics]

}
