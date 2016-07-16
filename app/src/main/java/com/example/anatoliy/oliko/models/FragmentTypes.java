package com.example.anatoliy.oliko.models;

/**
 * Created by anatoliy on 18/02/16.
 */
public enum FragmentTypes {

    MAIN, EDITOR, HISTORY, USER_PROFILE;

    public static FragmentTypes fromInt(int index){

        if (index == MAIN.ordinal()){
            return MAIN;
        } else if (index == EDITOR.ordinal()) {
            return EDITOR;
        } else if (index == HISTORY.ordinal()) {
            return HISTORY;
        } else if (index == USER_PROFILE.ordinal()){
            return USER_PROFILE;
        } else {
            throw new IllegalStateException("Unknown enum type found");
        }
    }
}