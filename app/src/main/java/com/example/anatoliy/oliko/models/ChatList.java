package com.example.anatoliy.oliko.models;

import android.graphics.Bitmap;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by anatoliy on 17/02/16.
 */
public class ChatList extends RealmObject {

    @PrimaryKey
    private String key;
    private String message;
    private String title;
    private String subtitle;

    private int messageCount;
    private Date creationDate;
    private Date lastTime;

    private int bitmapResourceID;

    public ChatList() {
        // Extends for Realm only, don't invoke manually
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getBitmapResourceID() {
        return bitmapResourceID;
    }

    public void setBitmapResourceID(int bitmapResourceID) {
        this.bitmapResourceID = bitmapResourceID;
    }
}
