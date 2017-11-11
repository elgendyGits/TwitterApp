package com.android.twitterapp.login.presenter;

import com.android.twitterapp.base.view.BaseView;
import com.android.twitterapp.login.model.LoggedUserRepository;
import com.android.twitterapp.login.view.LoginView;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by Mohamed Elgendy.
 */

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private LoggedUserRepository loggedUserRepository;
    private boolean isViewAttached;

    public LoginPresenterImpl(LoginView loginView, LoggedUserRepository loggedUserRepository) {
        this.loginView = loginView;
        this.loggedUserRepository = loggedUserRepository;
    }

    @Override
    public void onLoginSuccess(Result<TwitterSession> result) {

        //This provides TwitterSession as a result
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        loggedUserRepository.saveUserSession(session);
        loginView.navigateToHomeScreen();
    }

    @Override
    public void onLoginFailure(TwitterException exception) {
        if(isViewAttached){
            loginView.showInlineError(exception.getLocalizedMessage());
        }
    }

    @Override
    public void onViewAttached(BaseView view) {
        isViewAttached = true;
    }

    @Override
    public void onViewDetached() {
        isViewAttached = false;
    }
}
