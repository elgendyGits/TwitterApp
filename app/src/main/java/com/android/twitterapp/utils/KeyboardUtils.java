package com.android.twitterapp.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Mohamed Elgendy.
 */

public class KeyboardUtils {

    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void showSoftKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }
}
