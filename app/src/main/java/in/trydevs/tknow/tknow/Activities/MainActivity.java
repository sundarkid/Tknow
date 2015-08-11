package in.trydevs.tknow.tknow.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.ArrayList;
import java.util.List;

import in.trydevs.tknow.tknow.Adapters.MyAdapterPost;
import in.trydevs.tknow.tknow.DataClasses.Post;
import in.trydevs.tknow.tknow.GCM.QuickstartPreferences;
import in.trydevs.tknow.tknow.GCM.RegistrationIntentService;
import in.trydevs.tknow.tknow.R;
import in.trydevs.tknow.tknow.extras.MyApplication;
import in.trydevs.tknow.tknow.extras.SpacesItemDecoration;

public class MainActivity extends AppCompatActivity {


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;

    // For gcm
    MyAdapterPost adapterPost;
    private Toolbar toolbar;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private BroadcastReceiver mNewMessageBroadcastReciever;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("checking flow", "on pause");
        initialize();
    }

    private void initialize() {
        // Setting Top bar 'Tool bar'
        toolbar = (Toolbar) findViewById(R.id.top_bar_main_activity);
        setSupportActionBar(toolbar);
        // Setting up Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMainActivity);
        List<Post> data = new ArrayList<>();
        MyApplication.getWritableDatabase().insertPostData(data);
        adapterPost = new MyAdapterPost(MainActivity.this, MyApplication.getWritableDatabase().getPostData());
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.addItemDecoration(new SpacesItemDecoration(5));
        recyclerView.setAdapter(adapterPost);
        // Getting ready GCM
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (!sentToken) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertBuilder.setTitle(getResources().getString(R.string.token_not_got_title));
                    alertBuilder.setMessage(getResources().getString(R.string.token_not_got));
                    alertBuilder
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Todo implement something when ok is clicked
                                }
                            });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();
                }
            }
        };
        mNewMessageBroadcastReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Post post = intent.getParcelableExtra(getString(R.string.newMessage_broadcast));
                if (post != null)
                    adapterPost.NewDataAdded(post);
            }
        };
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("checking flow", "on post resume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(this).registerReceiver(mNewMessageBroadcastReciever,
                new IntentFilter(getString(R.string.newMessage_broadcast)));
    }

    @Override
    protected void onPause() {
        Log.d("checking flow", "on pause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNewMessageBroadcastReciever);
        super.onPause();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_people_info) {
            Intent intentPeople = new Intent(MainActivity.this, PeopleActivity.class);
            startActivity(intentPeople);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
