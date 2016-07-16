package com.example.anatoliy.oliko.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.anatoliy.oliko.utils.Prefs;

/**
 * Created by anatoliy on 27/02/16.
 */
public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        proceedToNextActivity();
    }

    private void proceedToNextActivity() {

        if (Prefs.isFirstTimeLaunch()){

            // Disable tutorial flow for future runs
            Prefs.setFirstTimeLaunch(false);

            Intent intent = new Intent(LauncherActivity.this, TutorialActivity.class);
            startActivity(intent);

        } else {

            if (Prefs.isSignedIn()){
                // User is signed in, take him directly to Main
                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(intent);

            } else {
                // User not passed registration process, proceed to registration
                Intent intent = new Intent(LauncherActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        }
        // Finish {@link LauncherActivity}
        finish();
    }
}
