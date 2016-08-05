package com.example.anatoliy.oliko.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.adapters.swipable.TutorialAdapter;
import com.example.anatoliy.oliko.listeners.TutorialCallbacks;
import com.example.anatoliy.oliko.utils.Prefs;
import com.viewpagerindicator.TitlePageIndicator;

/**
 * Represents set of tutorial pages (Partially implemented since it is POC)
 *
 * Created by anatoliy on 27/02/16.
 */
public class TutorialActivity extends BaseActivity implements TutorialCallbacks {

    public static final String TAG = TutorialActivity.class.getSimpleName();

    private TutorialAdapter mAdapter;
    private ViewPager mViewPager;
    private com.viewpagerindicator.TitlePageIndicator mPageIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_layout);
        init();
    }

    private void init() {

        mViewPager = (ViewPager) findViewById(R.id.vpTutorialViewPager);
        mAdapter = new TutorialAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mPageIndicator = (TitlePageIndicator) findViewById(R.id.tpiTutorialPageIndicator);

        if (mPageIndicator != null){
            mPageIndicator.setViewPager(mViewPager);
        }
    }

    @Override
    public void onContinueClicked() {

        startNextActivityAndFinish();
    }

    private void startNextActivityAndFinish(){

        Intent intent;

        if (Prefs.isSignedIn()){
            intent = new Intent(TutorialActivity.this, MainActivity.class);
        } else {
            intent = new Intent(TutorialActivity.this, RegistrationActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
