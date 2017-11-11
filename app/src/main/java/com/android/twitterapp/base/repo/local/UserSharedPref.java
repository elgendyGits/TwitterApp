package com.android.twitterapp.base.repo.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Mohamed Elgendy.
 */

public class UserSharedPref {

    public static final String PREFERENCE_USER_ID_KEY = "user_id_key";
    private static final long PREFERENCE_USER_ID_DEFAULT = 0;
    public static final String PREFERENCE_USER_NAME_KEY = "user_name_key";
    private static final String PREFERENCE_USER_NAME_DEFAULT = "";


    private UserSharedPref() {
        // prevent any instance from this class
    }

    public static long getLoggedUserId(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getLong(PREFERENCE_USER_ID_KEY, PREFERENCE_USER_ID_DEFAULT);
    }

    public static void setLoggedUserId(Context context , long userId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(PREFERENCE_USER_ID_KEY, userId);
        editor.apply();
    }

    public static String getLoggedUserName(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(PREFERENCE_USER_NAME_KEY, PREFERENCE_USER_NAME_DEFAULT);
    }

    public static void setLoggedUserName(Context context , String userName) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFERENCE_USER_NAME_KEY, userName);
        editor.apply();
    }
}
