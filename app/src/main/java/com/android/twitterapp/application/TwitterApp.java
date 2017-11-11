package com.android.twitterapp.application;

import android.app.Application;

import com.twitter.sdk.android.core.Twitter;

/**
 * Created by Mohamed Elgendy.
 */

public class TwitterApp extends Application {

    private static TwitterApp instance;

    public static TwitterApp getInstance() {
        if (instance == null){
            throw new IllegalStateException("Something went horribly wrong!!, no application attached!");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //Initializing twitter instance
        Twitter.initialize(this);
    }
}
