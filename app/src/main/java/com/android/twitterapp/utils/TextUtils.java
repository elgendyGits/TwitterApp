package com.android.twitterapp.utils;

import android.support.annotation.StringRes;

import com.android.twitterapp.application.TwitterApp;


/**
 * Created by Mohamed Elgendy.
 */

public class TextUtils {

    private static final String EMPTY_STRING_PATTERN = "^$|\\s+";

    public static String getString(@StringRes int resId) {
        return TwitterApp.getInstance().getString(resId);
    }

    public static boolean isEmptyString(String str) {
        if (str == null || str.length() == 0 ||
                str.matches(EMPTY_STRING_PATTERN)) {
            return true;
        }
        return false;
    }
}
