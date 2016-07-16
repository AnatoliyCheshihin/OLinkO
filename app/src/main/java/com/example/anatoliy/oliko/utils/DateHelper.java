package com.example.anatoliy.oliko.utils;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anatoliy on 20/02/16.
 */
public class DateHelper {

    public static final String SHORT_DATE_HOUR_FORMAT = "yyyy-MMM-dd HH:mm";
    public static final String DATE_HOUR_FORMAT = "MM/dd/yyyy HH:mm:ss.SSS";

    /*"yyyy-MM-dd",
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd HH:mmZ",
            "yyyy-MM-dd HH:mm:ss.SSSZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ"*/

    public static String dateHourFromDate(@NonNull Date date){

        /*SimpleDateFormat sdf = new SimpleDateFormat(DATE_HOUR_FORMAT);
        Date dt = new Date();
        String S = sdf.format(date); // formats to 09/23/2009 13:53:28.238
        Date dt2 = sdf.parse(S); // parses back*/

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_HOUR_FORMAT);
        String dateAsString = sdf.format(date); // formats to 09/23/2009 13:53:28.238
        return dateAsString;
    }

    public static String shortDateHourFromDate(@NonNull Date date){

        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_HOUR_FORMAT);
        String dateAsString = sdf.format(date); // formats to 09/23/2009 13:53:28.238
        return dateAsString;
    }
}
