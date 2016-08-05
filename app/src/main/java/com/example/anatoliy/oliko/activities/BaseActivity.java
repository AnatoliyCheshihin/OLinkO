package com.example.anatoliy.oliko.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Base activity that will be "bone" for rest activities utilized within app, please make here
 * modifications if they are related to all activities within app
 *
 * Created by anatoliy on 20/02/16.
 */
public class BaseActivity extends AppCompatActivity {

    public static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
