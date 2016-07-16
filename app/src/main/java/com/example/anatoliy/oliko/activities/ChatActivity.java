package com.example.anatoliy.oliko.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.fragments.ChatFragment;

/**
 * Created by anatoliy on 16/07/2016.
 */

public class ChatActivity extends BaseActivity {

    private static final String TAG = ChatActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        setChatFragment();
    }

    public void setChatFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null){
            fragmentManager.beginTransaction().add(R.id.flChatActivityFragmentContainer,
                    ChatFragment.newInstance(), ChatFragment.TAG).addToBackStack(null).commit();
        }
    }

}
