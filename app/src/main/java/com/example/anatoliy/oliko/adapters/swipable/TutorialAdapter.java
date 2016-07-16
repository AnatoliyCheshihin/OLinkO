package com.example.anatoliy.oliko.adapters.swipable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.anatoliy.oliko.fragments.TutorialPageFragment;

/**
 * Created by anatoliy on 27/02/16.
 */
public class TutorialAdapter extends FragmentPagerAdapter {

    public static final String TAG = TutorialAdapter.class.getSimpleName();

    public TutorialAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {

        return TutorialPageFragment.newInstance(position);
    }

    @Override
    public int getCount() {

        return TutorialPageFragment.getPageCount();
    }
}
