package com.example.anatoliy.oliko.models.chat;

/**
 * Created by anatoliy on 16/07/2016.
 */

public class ChatMessage {

    private String mMessage;
    private int mBitmapResource;
    private MessageType mMessageType;

    public static final int NO_IMAGE = 0;

    public ChatMessage(MessageType messageType, String message) {

        mMessage = message;
        mMessageType = messageType;
        mBitmapResource = NO_IMAGE;
    }

    public ChatMessage(MessageType messageType, int bitmapResource){

        mBitmapResource = bitmapResource;
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


    public int getBitmapResource(){
        return mBitmapResource;
    }

    public void setmBitmapResource(final int bitmapResource){
        mBitmapResource = bitmapResource;
    }

}
