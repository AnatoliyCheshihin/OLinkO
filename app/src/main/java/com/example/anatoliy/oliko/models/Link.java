package com.example.anatoliy.oliko.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by anatoliy on 17/02/16.
 */
public class Link extends RealmObject {

    @PrimaryKey
    private String key;
    private String value;
    // private boolean isPhoneNumber;
    private Date creationDate;
    private Date lastTimeUsedDate;
    private boolean favorite;

    public Link() {
        // Extends for Realm only, don't invoke manually
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /*public boolean isPhoneNumber() {
        return isPhoneNumber;
    }

    public void setPhoneNumber(boolean phoneNumber) {
        isPhoneNumber = phoneNumber;
    }*/

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastTimeUsedDate() {
        return lastTimeUsedDate;
    }

    public void setLastTimeUsedDate(Date lastTimeUsedDate) {
        this.lastTimeUsedDate = lastTimeUsedDate;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
