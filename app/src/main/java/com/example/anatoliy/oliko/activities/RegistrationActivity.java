package com.example.anatoliy.oliko.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.fragments.SignInFragment;
import com.example.anatoliy.oliko.fragments.SignUpFragment;
import com.example.anatoliy.oliko.listeners.RegistrationCallbacks;
import com.example.anatoliy.oliko.utils.Prefs;

/**
 * Provides default (fake since it is POC) registration functionality to user, in order to skip
 * registration just press 'SKIP'
 *
 * Created by anatoliy on 27/02/16.
 */
public class RegistrationActivity extends BaseActivity implements RegistrationCallbacks{

    public static final String TAG = RegistrationActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_layout);

        setSignInFragment();
    }

    public void setSignInFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null){
            fragmentManager.beginTransaction().add(R.id.rlRegistrationFragmentContainer,
                    new SignInFragment(), SignInFragment.TAG).addToBackStack(null).commit();
        }
    }

    private void replaceToSignUpFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager != null){

            // Find previous instance if exists
            Fragment fragment = fragmentManager.findFragmentByTag(SignUpFragment.TAG);

            if (fragment != null){
                fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
            }

            // Get new instance
            fragment = SignUpFragment.newInstance();

            fragmentManager.beginTransaction().replace(R.id.rlRegistrationFragmentContainer,
                    fragment, SignUpFragment.TAG).addToBackStack(SignUpFragment.TAG).commit();

        }
    }

    @Override
    public void onSignUpBackClicked() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null){
            // Return to previous fragment
            fragmentManager.popBackStack();
        }
    }

    @Override
    public void onSkipClicked() {

        proceedToMainActivityAndFinish();
    }

    @Override
    public void onNotRegisteredClicked(){
        replaceToSignUpFragment();
    }

    @Override
    public void onSignInClicked() {

        // User correctly entered data

        // Make user signed in
        Prefs.setSignedIn(true);
        // Proceed to Main and finish
        proceedToMainActivityAndFinish();
    }

    private void proceedToMainActivityAndFinish(){

        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRegistrationAccomplished() {

        // User is signed in
        Prefs.setSignedIn(true);
        // Proceed to Main
        proceedToMainActivityAndFinish();
    }
}
