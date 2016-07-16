package com.example.anatoliy.oliko.utils;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by anatoliy on 17/02/16.
 */
public class IntentHelper {

    public static Intent getCallIntent(@NonNull String phoneNumber){

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        return callIntent;
    }

    public static Intent getUrlIntent(@NonNull String url){

        Intent urlIntent = new Intent(Intent.ACTION_VIEW);
        urlIntent.setData(Uri.parse(url));
        return urlIntent;
    }
}
