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
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import in.trydevs.tknow.tknow.Adapters.MyFragmentAdapter;
import in.trydevs.tknow.tknow.GCM.QuickstartPreferences;
import in.trydevs.tknow.tknow.GCM.RegistrationIntentService;
import in.trydevs.tknow.tknow.R;
import in.trydevs.tknow.tknow.tabs.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    MyFragmentAdapter fragmentAdapter;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    // For gcm
    private Toolbar toolbar;
    private BroadcastReceiver mRegistrationBroadcastReceiver;


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
        // Setting up view pager and adapter
        viewPager = (ViewPager) findViewById(R.id.view_pager_main_activity);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout_main_activity);
        fragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        slidingTabLayout.setCustomTabView(R.layout.white_text_tab, R.id.textView);
        slidingTabLayout.setViewPager(viewPager);

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
    }

    @Override
    protected void onPause() {
        Log.d("checking flow", "on pause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
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
