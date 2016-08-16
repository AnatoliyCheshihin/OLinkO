package com.example.anatoliy.oliko.models;

import io.realm.RealmObject;

/**
 * Created by vhnvn on 8/16/16.
 */
public class Ads extends RealmObject {
    private int bitmapResourceID;

    public Ads(){}

    public int getBitmapResourceID() {
        return bitmapResourceID;
    }

    public void setBitmapResourceID(int bitmapResourceID) {
        this.bitmapResourceID = bitmapResourceID;
    }
}
