package com.example.anatoliy.oliko.models.chat;

import android.graphics.Bitmap;

/**
 * Created by anatoliy on 16/07/2016.
 */

public class ChatMessage {

    private String mMessage;
    private Bitmap mBitmap;
    private MessageType mMessageType;

    public static final int NO_IMAGE = 0;

    public ChatMessage(MessageType messageType, String message) {

        mMessage = message;
        mMessageType = messageType;
        mBitmap = null;
    }

    public ChatMessage(MessageType messageType, Bitmap bitmap){

        mBitmap = bitmap;
        mMessageType = messageType;
        mMessage = null;
    }

    public MessageType getMessageType() {
        return mMessageType;
    }

    public void setMessageType(MessageType messageType) {
        this.mMessageType = messageType;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }


    public Bitmap getBitmap(){
        return mBitmap;
    }

    public void setmBitmap(final Bitmap bitmap){
        mBitmap = bitmap;
    }

}
