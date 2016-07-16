package com.example.anatoliy.oliko.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anatoliy.oliko.R;
import com.example.anatoliy.oliko.adapters.regular.ChatAdapter;
import com.example.anatoliy.oliko.models.chat.ChatMessage;
import com.example.anatoliy.oliko.models.chat.MessageType;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by anatoliy on 16/07/2016.
 */

public class ChatFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = ChatFragment.class.getSimpleName();

    public static final String DATA_LIST_KEY = "data_list_key";

    private static final long MINIMAL_RESPONSE_TIME = 1_000L;
    private static final String EMPTY_INPUT = "";

    private List<ChatMessage> mDataList;

    private ImageView mPostImage;
    private TextView mPostMessage;
    private EditText mMessageInput;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ChatAdapter mAdapter;

    private AtomicBoolean mUserAnswered = new AtomicBoolean(true);

    public static ChatFragment newInstance(){
        return new ChatFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat_layout, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvChatRecyclerView);
        mPostImage = (ImageView) view.findViewById(R.id.ivChatPostImage);
        mPostMessage = (TextView) view.findViewById(R.id.tvChatPostMessage);
        mMessageInput = (EditText) view.findViewById(R.id.etChatInput);

        mPostImage.setOnClickListener(this);
        mPostMessage.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    private void init() {

        mDataList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        // Specify to show images from end of the list
        mLayoutManager.setStackFromEnd(true);

        mAdapter = new ChatAdapter(mDataList);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(false);

        mRecyclerView.setAdapter(mAdapter);
    }

    private void handlePostImageClick(){
        // TODO: Implement
    }

    private void handlePostMessageClick(){

        final String message = mMessageInput.getText().toString();

        if (mUserAnswered.get() && !message.isEmpty()){

            final ChatMessage ownerMessage = new ChatMessage(MessageType.OWNER, message);
            mAdapter.addItem(ownerMessage);
            clearInput();

            postResponseMessage(ownerMessage);
        }
    }

    private void clearInput(){
        mMessageInput.setText(EMPTY_INPUT);
    }

    private void postResponseMessage(ChatMessage ownerMessage) {
        new ResponseThread(ChatFragment.this, ownerMessage).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivChatPostImage:
                handlePostImageClick();
                break;
            case R.id.tvChatPostMessage:
                handlePostMessageClick();
                break;
            default:
                // do nothing
                 break;
        }
    }

    protected static class ResponseThread extends Thread {

        public WeakReference<ChatFragment> mOuterFragment;
        public ChatMessage mOwnerMessage;

        private static Random sRandomTime = new Random();

        public ResponseThread(ChatFragment fragment, ChatMessage ownerMessage) {

            mOuterFragment = new WeakReference<ChatFragment>(fragment);
            mOwnerMessage = ownerMessage;
        }

        @Override
        public void run() {
            super.run();

            if (mOuterFragment.get() != null){
                // Implement here logic that depends on owner message
                if (mOuterFragment.get().mUserAnswered.compareAndSet(true, false)){

                    try {
                        Thread.sleep(MINIMAL_RESPONSE_TIME
                                +  sRandomTime.nextInt(2)*MINIMAL_RESPONSE_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    final ChatMessage userMessage = new ChatMessage(MessageType.USER, "user message");

                    mOuterFragment.get().mMessageInput.post(new Runnable() {
                        @Override
                        public void run() {
                            mOuterFragment.get().mAdapter.addItem(userMessage);
                        }
                    });
                    mOuterFragment.get().mUserAnswered.set(true);
                }
            }
        }
    }
}
