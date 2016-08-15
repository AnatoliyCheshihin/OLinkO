package com.example.anatoliy.oliko.listeners;

import android.support.annotation.NonNull;

import com.example.anatoliy.oliko.models.Link;

/**
 * Listener for requests from {@link com.example.anatoliy.oliko.fragments.MainFragment} to
 * {@link com.example.anatoliy.oliko.activities.MainActivity} in order to preserve correct
 * android architecture and not invoke activities directly from fragment
 * Created by anatoliy on 05/08/2016.
 */

public interface MainFragmentClickListener {
    void onChatActivityStartRequest(String initialMessage);
    void onProceedViaLinkRequest(@NonNull Link link);
}
