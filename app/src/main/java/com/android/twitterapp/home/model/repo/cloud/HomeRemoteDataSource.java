package com.android.twitterapp.home.model.repo.cloud;

import com.android.twitterapp.application.TwitterApp;
import com.android.twitterapp.base.repo.local.UserSharedPref;
import com.android.twitterapp.home.model.repo.HomeDataSource;
import com.android.twitterapp.custom.timeline.base.HomeTimeline;


/**
 * Created by Mohamed Elgendy.
 */

public class HomeRemoteDataSource implements HomeDataSource {

    private static HomeRemoteDataSource INSTANCE;
    HomeTimeline homeTimeline;

    // Prevent direct instantiation.
    private HomeRemoteDataSource() {
    }

    public static HomeRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HomeRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public HomeTimeline getTimeline() {
        homeTimeline = new HomeTimeline.Builder().userId(UserSharedPref.getLoggedUserId(TwitterApp.getInstance())).build();
        return homeTimeline;
    }
}

