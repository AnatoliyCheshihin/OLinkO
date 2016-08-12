package com.example.anatoliy.oliko.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.fragments.ChatFragment;

/**
 * Activity that provides chat 'like' representation of dialog between user and manager or
 * bot (currently bot behavior expected)
 *
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
            ChatFragment chatFragment = ChatFragment.newInstance();
            Bundle arguments = new Bundle();
            arguments.putString("roomIdentifier", getIntent().getStringExtra("roomIdentifier"));
            chatFragment.setArguments(arguments);
            fragmentManager.beginTransaction().add(R.id.flChatActivityFragmentContainer,
                    chatFragment, ChatFragment.TAG).commit();
        }
    }

}
