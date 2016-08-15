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
import com.example.anatoliy.oliko.helpers.RealmHelper;
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
import java.util.Date;
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

    private ArrayList<Runnable> mRunnableQueue = new ArrayList<>();

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
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        final String message = getArguments().getString("initialMessage");
        if("סלקום טי וי".equals(message) || "test1".equals(message)){
            mRunnableQueue.add(new Runnable() {
                @Override
                public void run() {
                    final ChatMessage newMessage = new ChatMessage(MessageType.OWNER, "מתעניין בסלקום טי וי");
                    mMessageInput.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addItem(newMessage);
                        }
                    });
                }
            });
            mRunnableQueue.add(new Runnable() {
                @Override
                public void run() {
                    final ChatMessage newMessage = new ChatMessage(MessageType.USER, "האם לשלוח שם, טלפון ומייל לחברה?");
                    mMessageInput.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addItem(newMessage);
                        }
                    });
                }
            });
            mRunnableQueue.add(new Runnable() {
                @Override
                public void run() {
                    final ChatMessage newMessage = new ChatMessage(MessageType.OWNER, "ישראל ישראלי\n" +
                            "05412345678\n" +
                            "israel@gmail.com\n");
                    mMessageInput.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addItem(newMessage);
                        }
                    });
                }
            });
            mRunnableQueue.add(new Runnable() {
                @Override
                public void run() {
                    final ChatMessage newMessage = new ChatMessage(MessageType.USER, BitmapFactory.decodeResource(getResources(), R.drawable.sample) );
                    mMessageInput.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addItem(newMessage);
                        }
                    });
                }
            });
            RealmHelper.addOrCreateChatList("cellcom","Cellcom","Cellcom TV promotion",new Date(),3);
        }else if("volvo 40".equals(message)){
            mRunnableQueue.add(new Runnable() {
                @Override
                public void run() {
                    final ChatMessage newMessage = new ChatMessage(MessageType.OWNER, "volvo 40" );
                    mMessageInput.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addItem(newMessage);
                        }
                    });
                }
            });
            mRunnableQueue.add(new Runnable() {
                @Override
                public void run() {
                    final ChatMessage newMessage = new ChatMessage(MessageType.USER, BitmapFactory.decodeResource(getResources(), R.drawable.sample) );
                    final ChatMessage newMessage2 = new ChatMessage(MessageType.USER, "http://www.volvocars.com/intl/cars/new-models/v40");
                    mMessageInput.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addItem(newMessage);
                            mAdapter.addItem(newMessage2);
                        }
                    });
                }
            });
            RealmHelper.addOrCreateChatList("volvo","Volvo","Volvo group",new Date(),1);
        }else if("Knight Frank".equals(message)){
            mRunnableQueue.add(new Runnable() {
                @Override
                public void run() {
                    final ChatMessage newMessage = new ChatMessage(MessageType.OWNER, "Knight Frank 3003493" );
                    mMessageInput.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addItem(newMessage);
                        }
                    });
                }
            });
            mRunnableQueue.add(new Runnable() {
                @Override
                public void run() {
                    final ChatMessage newMessage = new ChatMessage(MessageType.USER, BitmapFactory.decodeResource(getResources(), R.drawable.sample) );
                    final ChatMessage newMessage2 = new ChatMessage(MessageType.USER, "http://search.knightfrank.com/3003493");
                    mMessageInput.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addItem(newMessage);
                            mAdapter.addItem(newMessage2);
                        }
                    });
                }
            });
            RealmHelper.addOrCreateChatList("kf","Knight Frank","Property #3003494",new Date(),0);
        }else if("Black Crown".equals(message)){
            mRunnableQueue.add(new Runnable() {
                @Override
                public void run() {
                    final ChatMessage newMessage = new ChatMessage(MessageType.OWNER, "+tasteis" );
                    mMessageInput.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addItem(newMessage);
                        }
                    });
                }
            });
            mRunnableQueue.add(new Runnable() {
                @Override
                public void run() {
                    final ChatMessage newMessage = new ChatMessage(MessageType.USER, "Please upload photo" );
                    mMessageInput.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addItem(newMessage);
                        }
                    });
                }
            });
            mRunnableQueue.add(new Runnable() {
                @Override
                public void run() {
                    final ChatMessage newMessage = new ChatMessage(MessageType.OWNER, BitmapFactory.decodeResource(getResources(), R.drawable.sample));
                    mMessageInput.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addItem(newMessage);
                        }
                    });
                }
            });
            mRunnableQueue.add(new Runnable() {
                @Override
                public void run() {
                    final ChatMessage newMessage = new ChatMessage(MessageType.USER, "You won! Black Crown 6 pack\n" +
                            "Please enter your full name and address" );
                    mMessageInput.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addItem(newMessage);
                        }
                    });
                }
            });
            RealmHelper.addOrCreateChatList("kf","Knight Frank","Property #3003494",new Date(),0);
        }
        delayedRunProcess();
    }

    private void handlePostImageClick(){
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(getContext());
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }

    private void handlePostMessageClick(){

        final String message = mMessageInput.getText().toString();

        if (!message.isEmpty()){
            final ChatMessage ownerMessage = new ChatMessage(MessageType.OWNER, message);
            mAdapter.addItem(ownerMessage);
            clearInput();
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

    synchronized void delayedRunProcess(){
        if(mRunnableQueue!=null && mRunnableQueue.size()>0){
            Runnable r = mRunnableQueue.remove(0);
            r.run();
            mMessageInput.postDelayed(new Runnable() {
                @Override
                public void run() {
                    delayedRunProcess();
                }
            }, 700);
        }
    }
}
