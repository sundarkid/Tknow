package in.trydevs.tknow.tknow.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.trydevs.tknow.tknow.Fragments.FragmentChatRoom;
import in.trydevs.tknow.tknow.Fragments.FragmentClub;
import in.trydevs.tknow.tknow.Fragments.FragmentGlobalPost;
import in.trydevs.tknow.tknow.Fragments.FragmentImages;
import in.trydevs.tknow.tknow.R;
import in.trydevs.tknow.tknow.extras.MyApplication;

/**
 * Created by Sundareswaran on 25-08-2015.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {
    String tabNames[];

    public MyFragmentAdapter(FragmentManager fm) {
        super(fm);
        tabNames = MyApplication.getAppContext().getResources().getStringArray(R.array.tabs_main_activity);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FragmentGlobalPost();
                break;
            case 1:
                fragment = new FragmentClub();
                break;
            case 2:
                fragment = new FragmentImages();
                break;
            case 3:
                fragment = new FragmentChatRoom();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }
}
