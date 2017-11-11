package com.android.twitterapp.login.model.repo.local;

import android.content.Context;
import android.util.Log;

import com.android.twitterapp.base.repo.local.UserSharedPref;
import com.android.twitterapp.login.model.repo.LoggedUserDataSource;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by Mohamed Elgendy.
 */

public class LoggedUserLocalDataSource  implements LoggedUserDataSource {

    private static LoggedUserLocalDataSource INSTANCE;
    private Context context;

    // Prevent direct instantiation.
    private LoggedUserLocalDataSource(Context context) {
        this.context = context;
    }

    public static LoggedUserLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LoggedUserLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void saveUserSession(TwitterSession session) {
        TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;

        //save user id & name to sharedPref
        UserSharedPref.setLoggedUserId(context, session.getUserId());
        UserSharedPref.setLoggedUserName(context, session.getUserName());
    }
}
