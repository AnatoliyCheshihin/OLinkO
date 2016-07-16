package com.example.anatoliy.oliko.adapters.swipable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.example.anatoliy.oliko.activities.MainActivity;
import com.example.anatoliy.oliko.fragments.EditorFragment;
import com.example.anatoliy.oliko.fragments.FavoritesFragment;
import com.example.anatoliy.oliko.fragments.HistoryFragment;
import com.example.anatoliy.oliko.fragments.MainFragment;
import com.example.anatoliy.oliko.fragments.UserProfileFragment;
import com.example.anatoliy.oliko.models.FragmentTypes;

/**
 * Created by anatoliy on 17/02/16.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private MainActivity mActivity;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragmentAdapter(FragmentManager fm, AppCompatActivity activity) {
        super(fm);
        if (activity instanceof MainActivity) {
            mActivity = (MainActivity) activity;
        }
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (FragmentTypes.fromInt(position)){
            case EDITOR:
                fragment = new EditorFragment();
                break;
            case MAIN:
                fragment = new MainFragment();
                break;
            case HISTORY:
                fragment = HistoryFragment.newInstance();
                break;
            case USER_PROFILE:
                fragment = new UserProfileFragment();
                break;
            /*case FAVORITES:
                fragment = new FavoritesFragment();*/
            default:
                throw new IllegalStateException("Unknown position");
        }
        return fragment;
    }

    @Override
    public int getCount() {

        return FragmentTypes.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (FragmentTypes.fromInt(position)){
            case EDITOR:
                return "Admin";
            case MAIN:
                return "Main";
            case HISTORY:
                return "History";
            case USER_PROFILE:
                return "Profile";
            /*case FAVORITES:
                return "Favorites";*/
            default:
                throw new IllegalStateException("Unknown position");
        }
    }
}
