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

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.trydevs.tknow.tknow.Activities.MainActivity;
import in.trydevs.tknow.tknow.DataClasses.People;
import in.trydevs.tknow.tknow.DataClasses.Post;
import in.trydevs.tknow.tknow.DataClasses.UrlLinkNames;
import in.trydevs.tknow.tknow.R;
import in.trydevs.tknow.tknow.extras.MyApplication;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */
        Log.d("message", message);
        List<Post> posts = Collections.emptyList();
        List<People> peoples = Collections.emptyList();
        try {
            JSONObject object = new JSONObject(message);
            // Getting peoples details if we have any
            if (object.has(UrlLinkNames.getJsonPeople())) {
                JSONArray jsonArrayPeople = new JSONArray(object.getString(UrlLinkNames.getJsonPeople()));
                peoples = new ArrayList<>();
                for (int i = 0; i < jsonArrayPeople.length(); i++) {
                    JSONObject objectPeople = jsonArrayPeople.getJSONObject(i);
                    People people = People.getPeopleFromJson(objectPeople);
                    peoples.add(people);
                }
                MyApplication.getWritableDatabase().insertPeopleData(peoples);
            }
            if (object.has(UrlLinkNames.getJsonPost())) {
                // Getting posts if we have any
                JSONArray jsonArrayPost = new JSONArray(object.getString(UrlLinkNames.getJsonPost()));
                posts = new ArrayList<>();
                for (int i = 0; i < jsonArrayPost.length(); i++) {
                    JSONObject objectPost = jsonArrayPost.getJSONObject(i);
                    Post post = Post.fromJSON(objectPost);
                    posts.add(post);
                }
            }
            MyApplication.getWritableDatabase().insertPostData(posts);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (posts.size() > 0) {
            Intent intent = new Intent(getString(R.string.newMessage_broadcast));
            intent.putExtra(getString(R.string.newMessage_broadcast), posts.get(0));
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            /**
             * In some cases it may be useful to show a notification indicating to the user
             * that a message was received.
             */
            sendNotification(message);
        }
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.techknowlogy))
                .setContentText(getString(R.string.newMessage))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
