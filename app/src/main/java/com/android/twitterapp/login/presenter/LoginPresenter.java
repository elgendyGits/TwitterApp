package com.android.twitterapp.login.presenter;

import com.android.twitterapp.base.presenter.BasePresenter;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by Mohamed Elgendy.
 */

public interface LoginPresenter extends BasePresenter {
    void onLoginSuccess(Result<TwitterSession> result);
    void onLoginFailure(TwitterException exception);
}
