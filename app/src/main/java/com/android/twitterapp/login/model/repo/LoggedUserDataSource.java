package com.android.twitterapp.login.model.repo;

import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by Mohamed Elgendy.
 */

public interface LoggedUserDataSource {
    void saveUserSession(TwitterSession session);
}
