package com.example.anatoliy.oliko.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
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
import com.example.anatoliy.oliko.utils.ImagePicker;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URISyntaxException;
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
    private static final int PICK_IMAGE_ID = 0x808;

    private List<ChatMessage> mDataList;

    private ImageView mPostImage;
    private TextView mPostMessage;
    private EditText mMessageInput;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ChatAdapter mAdapter;

    private WebSocketClient mSocket;

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

    @Override
    public void onResume() {
        super.onResume();

        if(mSocket==null){

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(mSocket!=null){
            mSocket.close();
            mSocket = null;
        }
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

        connectToServer();
    }

    @SuppressWarnings("ALL")
    private void connectToServer() {
        String roomID = getArguments().getString("roomIdentifier");
        if(mSocket!=null){
            try {
                mSocket.closeBlocking();
                mSocket = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            mSocket = new WebSocketClient(new URI("ws://lyric.vn:8088/room/"+roomID)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    if(mSocket == this){
                        systemMessage("You are connected!");
                    }
                }

                @Override
                public void onMessage(String message) {
                    if(mSocket == this){
                        try {
                            JSONObject messageJSON = new JSONObject(message);
                            Log.d(TAG,"Message json "+message);
                            if(messageJSON!=null && messageJSON.getString("type").equals("message")){
                                String chatText = messageJSON.getString("message");
                                if(chatText != null) {
                                    if (chatText.startsWith("msg:")) {
                                        final ChatMessage newMessage = new ChatMessage(MessageType.USER, chatText.substring(4));
                                        mMessageInput.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                mAdapter.addItem(newMessage);
                                            }
                                        });
                                    } else if (chatText.startsWith("sys:")) {
                                        final ChatMessage newMessage = new ChatMessage(MessageType.SYSTEM, chatText.substring(4));
                                        mMessageInput.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                mAdapter.addItem(newMessage);
                                            }
                                        });
                                    } else if (chatText.startsWith("bmp:")) {
                                        String base64 = chatText.substring(4);
                                        byte[] bytes = Base64.decode(base64, Base64.DEFAULT);

                                        final ChatMessage newMessage = new ChatMessage(MessageType.USER, BitmapFactory.decodeByteArray(bytes,0,bytes.length));
                                        mMessageInput.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                mAdapter.addItem(newMessage);
                                            }
                                        });
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    if(mSocket == this){
                        systemMessage("You are disconnected");
                    }
                }

                @Override
                public void onError(Exception ex) {
                    if(mSocket == this){
                        systemMessage("An error has been occurred...");
                    }
                }
            };
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void handlePostImageClick(){
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(getContext());
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }

    private void handlePostMessageClick(){

        final String message = mMessageInput.getText().toString();

        if( mSocket != null ){
            if(mSocket.getConnection().isConnecting()){
                systemMessage("Still connecting... Please wait...");
                return;
            }
            if(!mSocket.getConnection().isOpen()){
                systemMessage("Connection to server is not opened...");
                return;
            }
        }

        if (!message.isEmpty()){

            final ChatMessage ownerMessage = new ChatMessage(MessageType.OWNER, message);
            mAdapter.addItem(ownerMessage);
            clearInput();

            new Thread(){
                @Override
                public void run() {
                    mSocket.send("msg:"+message);
                }
            }.start();
        }
    }

    private void clearInput(){
        mMessageInput.setText(EMPTY_INPUT);
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

    void systemMessage(String message){
        final ChatMessage newMessage = new ChatMessage(MessageType.SYSTEM, message);
        mMessageInput.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.addItem(newMessage);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PICK_IMAGE_ID:
                Bitmap bitmap = ImagePicker.getImageFromResult(getContext(), resultCode, data);
                if(bitmap.getWidth()>=400 || bitmap.getHeight()>=400){
                    int w = bitmap.getWidth(), h = bitmap.getHeight();
                    double ratio = Math.min(400./w,400./h);
                    w = (int)(w*ratio); h = (int)(h*ratio);
                    Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, w, h, true);
                    bitmap.recycle();
                    bitmap = bitmap2;
                }
                final ChatMessage newMessage = new ChatMessage(MessageType.OWNER, bitmap);
                mMessageInput.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.addItem(newMessage);
                    }
                });
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                final String encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                new Thread(){
                    @Override
                    public void run() {
                        mSocket.send("bmp:"+encodedImage);
                    }
                }.start();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
