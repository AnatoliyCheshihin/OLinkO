package com.example.anatoliy.oliko.utils;

import android.text.TextUtils;

import org.w3c.dom.Text;

/**
 * Created by anatoliy on 17/02/16.
 */
public class Utils {

    public final static boolean isPhoneNumber(String text){
        if (text == null){
            throw new IllegalStateException("Must not be null");
        }

        if (text.length() == 0){
            return false;
        }

        return TextUtils.isDigitsOnly(text);
    }

    /**
     * Validates if char sequence has phone number structure
     *
     * @param target
     * @return
     */
    public final static boolean isValidPhoneNumber(CharSequence target) {


        if (target == null) {
            return false;
        } else {
            if (target.length() < 6 || target.length() > 13) {
                return false;
            } else {
                return android.util.Patterns.PHONE.matcher(target).matches();
            }
        }
    }
}
