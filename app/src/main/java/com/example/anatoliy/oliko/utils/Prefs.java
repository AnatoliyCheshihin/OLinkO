package com.example.anatoliy.oliko.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * Created by anatoliy on 27/02/16.
 */
public final class Prefs {

    public static final String TAG = Prefs.class.getSimpleName();

    private static volatile SharedPreferences sSharedPreferences;

    /**
     * Initializes SharedPreferences manager. Single instance ensured by double check lock
     * singleton implementation
     *
     * @param context consistent Application context (instance has to live during the whole
     *                               application lifecycle) make sure it
     *                               is {@link com.example.anatoliy.oliko.app.PointerPlusApplication}
     */
    public static void init(Context context){

        if (context == null){

            throw new NullPointerException("Initialization failed, context is null");
        }

        if (sSharedPreferences == null){
            synchronized (Prefs.class){
                if (sSharedPreferences == null){
                    sSharedPreferences = context.getSharedPreferences(context.getPackageName(),
                            Context.MODE_PRIVATE);
                }
            }
        }
    }

    /**
     * Returns instance of SharedPreferences
     *
     * @return {@link SharedPreferences}
     * @throws RuntimeException on method utilization attempt without prior initialization
     */
    public static SharedPreferences getPreferences(){

        if (sSharedPreferences != null){
            return sSharedPreferences;
        }

        throw new RuntimeException("Prefs Manager was not initialized. Please call " +
                "PrefsManager.init() from Application's subclass");
    }


    /**
     * Checks within SharedPreferences if current launch is very first launch for app
     * True if app is launched for the first time, false otherwise
     *
     * @return true|false
     */
    public static boolean isFirstTimeLaunch(){

        return getBoolean(Keys.FIRST_LAUNCH, true);
    }

    /**
     * Sets value if application was launched for the very first time
     *
     * @param firstTimeLaunch
     */
    public static void setFirstTimeLaunch(boolean firstTimeLaunch){

        putBoolean(Keys.FIRST_LAUNCH, firstTimeLaunch);
    }


    public static boolean isSignedIn(){

        return getBoolean(Keys.SIGNED_IN, false);
    }

    public static void setSignedIn(boolean signedIn){

        putBoolean(Keys.SIGNED_IN, signedIn);
    }

    // User profile are begin

    public static void setPassword(String password){

        putString(Keys.PASSWORD, password);
    }

    public static String getPassword(){

        return getString(Keys.PASSWORD, null);
    }

    public static void setFirstName(String firstName){

        putString(Keys.USER_FIRST_NAME, firstName);
    }

    public static void setLastName(String lastName){

        putString(Keys.USER_LAST_NAME, lastName);
    }

    public static void setEmail(String email){

        putString(Keys.USER_EMAIL, email);
    }

    public static void setPhone(String phone){

        putString(Keys.USER_PHONE, phone);
    }

    public static void setCountry(String country){

        putString(Keys.USER_COUNTRY, country);
    }

    public static void setLanguage(String language){

        putString(Keys.USER_LANGUAGE, language);
    }

    public static String getFirstName(){

        return getString(Keys.USER_FIRST_NAME, DefaultValues.STRING);
    }

    public static String getLastName(){

        return getString(Keys.USER_LAST_NAME, DefaultValues.STRING);
    }

    public static String getEmail(){

        return getString(Keys.USER_EMAIL, DefaultValues.STRING);
    }

    public static String getPhone(){

        return getString(Keys.USER_PHONE, DefaultValues.STRING);
    }

    public static String getCountry(){

        return getString(Keys.USER_COUNTRY, DefaultValues.STRING);
    }

    public static String getLanguage(){

        return getString(Keys.USER_LANGUAGE, DefaultValues.STRING);
    }

    // Put region

    /**
     * Stores a long value.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see android.content.SharedPreferences.Editor#putLong(String, long)
     */
    public static void putLong(final String key, final long value) {
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    /**
     * Stores an integer value.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see android.content.SharedPreferences.Editor#putInt(String, int)
     */
    public static void putInt(final String key, final int value) {
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    /**
     * Stores a double value as a long raw bits value.
     *
     * @param key   The name of the preference to modify.
     * @param value The double value to be save in the preferences.
     * @see android.content.SharedPreferences.Editor#putLong(String, long)
     */
    public static void putDouble(final String key, final double value) {
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(key, Double.doubleToRawLongBits(value));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    /**
     * Stores a float value.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see android.content.SharedPreferences.Editor#putFloat(String, float)
     */
    public static void putFloat(final String key, final float value) {
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putFloat(key, value);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    /**
     * Stores a boolean value.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see android.content.SharedPreferences.Editor#putBoolean(String, boolean)
     */
    public static void putBoolean(final String key, final boolean value) {
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    /**
     * Stores a String value.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see android.content.SharedPreferences.Editor#putString(String, String)
     */
    public static void putString(final String key, final String value) {
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    // End

    // Get region

    /**
     * Retrieves a stored int value.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException if there is a preference with this name that is not
     *                            an int.
     * @see android.content.SharedPreferences#getInt(String, int)
     */
    public static int getInt(final String key, final int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    /**
     * Retrieves a stored boolean value.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException if there is a preference with this name that is not a boolean.
     * @see android.content.SharedPreferences#getBoolean(String, boolean)
     */
    public static boolean getBoolean(final String key, final boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    /**
     * Retrieves a stored long value.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException if there is a preference with this name that is not a long.
     * @see android.content.SharedPreferences#getLong(String, long)
     */
    public static long getLong(final String key, final long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    /**
     * Returns the double that has been saved as a long raw bits value in the long preferences.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue the double Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException if there is a preference with this name that is not a long.
     * @see android.content.SharedPreferences#getLong(String, long)
     */
    public static double getDouble(final String key, final double defValue) {
        return Double.longBitsToDouble(getPreferences().getLong(key, Double.doubleToLongBits(defValue)));
    }

    /**
     * Retrieves a stored float value.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException if there is a preference with this name that is not a float.
     * @see android.content.SharedPreferences#getFloat(String, float)
     */
    public static float getFloat(final String key, final float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

    /**
     * Retrieves a stored String value.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException if there is a preference with this name that is not a String.
     * @see android.content.SharedPreferences#getString(String, String)
     */
    public static String getString(final String key, final String defValue) {
        return getPreferences().getString(key, defValue);
    }

    /** -------------------------------------------------------------------------------------------
     * Stores all keys required for accessing SharedPreferences data.
     * NOTE: all constants below have implicitly final static modifiers
     */
    public final class Keys {

        // Hidden constructor
        private Keys(){
            // Exists only to prevent from accidental instantiation
            throw new AssertionError();
        }

        public static final String FIRST_LAUNCH = "first_launch";

        public static final String SIGNED_IN = "signed_in";

        public static final String USER_FIRST_NAME = "user_first_name";
        public static final String USER_LAST_NAME = "user_last_name";
        public static final String USER_COUNTRY = "user_country";
        public static final String USER_EMAIL = "user_email";
        public static final String USER_PHONE = "user_phone";
        public static final String USER_LANGUAGE = "user_language";
        public static final String PASSWORD = "password";
    }

    /** -------------------------------------------------------------------------------------------
     * Stores default values required for storing and accessing them during SharedPrefsHelper
     * utilization.
     * NOTE: all constants below have implicitly final static modifiers
     */
    public final class DefaultValues{

        // Hidden constructor
        private DefaultValues(){
            // Exists only to prevent from accidental instantiation
            throw new AssertionError();
        }

        public static final long LONG = -1L;
        public static final int INTEGER = -1;
        public static final float FLOAT = -1.F;
        public static final double DOUBLE = -1.D;
        public static final boolean BOOLEAN = false;
        public static final String STRING = "";
    }
}