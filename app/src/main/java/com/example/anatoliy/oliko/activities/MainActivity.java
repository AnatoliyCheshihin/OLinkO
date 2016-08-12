package com.example.anatoliy.oliko.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.adapters.swipable.FragmentAdapter;
import com.example.anatoliy.oliko.helpers.RealmHelper;
import com.example.anatoliy.oliko.listeners.MainFragmentClickListener;
import com.example.anatoliy.oliko.models.Link;
import com.example.anatoliy.oliko.utils.IntentHelper;

import java.util.Date;

public class MainActivity extends BaseActivity implements MainFragmentClickListener {

    private CoordinatorLayout mCoordinatorLayout;
    private FragmentAdapter mAdapter;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.clCoordinatorLayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        initViewPager();
        initTabs();
    }

    private void initTabs() {

        mTabLayout = (TabLayout) findViewById(R.id.tlTabLayout);
        if (mTabLayout != null){
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }

    public void startActivityFromLink(@NonNull Link link){

        Intent intent = IntentHelper.getUrlIntent(link.getValue());
        // Set updated history date for current link
        RealmHelper.updateLinkHistory(link, new Date(System.currentTimeMillis()));
        // Proceed further
        startActivity(intent);

        showSnackBarMessage("Proceeding...", Snackbar.LENGTH_SHORT);
    }

    /**
     * Takes user to {@link ChatActivity}
     */
    public void proceedToChatActivity(String roomIdentifier){

        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
        intent.putExtra("roomIdentifier", roomIdentifier);
        startActivity(intent);
    }

    private void initViewPager() {

        mViewPager = (ViewPager) findViewById(R.id.vpMainViewPager);
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), MainActivity.this);
        mViewPager.setAdapter(mAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showSnackBarMessage(@NonNull String text, final int length){

        if (length != Snackbar.LENGTH_INDEFINITE
                && length != Snackbar.LENGTH_LONG
                && length != Snackbar.LENGTH_SHORT){

            throw new IllegalArgumentException("SnackBar length constant has not correct value");
        }

        Snackbar.make(mCoordinatorLayout, text, length).show();
    }

    public CoordinatorLayout getCoordinatorLayout(){
        return mCoordinatorLayout;
    }

    @Override
    public void onChatActivityStartRequest(String roomIdentifier) {
        proceedToChatActivity(roomIdentifier);
    }

    @Override
    public void onProceedViaLinkRequest(@NonNull Link link) {
        startActivityFromLink(link);
    }
}
