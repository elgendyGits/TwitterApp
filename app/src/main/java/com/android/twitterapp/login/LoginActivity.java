package com.android.twitterapp.login;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.twitterapp.R;
import com.android.twitterapp.application.TwitterApp;
import com.android.twitterapp.base.injector.Injection;
import com.android.twitterapp.base.view.BaseActivity;
import com.android.twitterapp.home.HomeActivity;
import com.android.twitterapp.login.presenter.LoginPresenter;
import com.android.twitterapp.login.presenter.LoginPresenterImpl;
import com.android.twitterapp.login.view.LoginView;
import com.android.twitterapp.utils.DialogUtils;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.layout_login)
    RelativeLayout loginLayout;

    @BindView(R.id.button_twitter_login)
    TwitterLoginButton twitterLoginButton;

    @BindView(R.id.background_one)
    ImageView backgroundOne;

    @BindView(R.id.background_two)
    ImageView backgroundTwo;


    // declare login presenter
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        addingCallback();
        initializePresenter();
        startAnimation();
    }

    private void startAnimation() {

        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(4000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX - width);
            }
        });
        animator.start();

    }

    private void addingCallback() {

        // Adding a callback to loginButton

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                loginPresenter.onLoginSuccess(result);
            }

            @Override
            public void failure(TwitterException exception) {
                loginPresenter.onLoginFailure(exception);
            }
        });
    }

    private void initializePresenter() {
        loginPresenter = new LoginPresenterImpl(this,
                Injection.provideLoggedUserRepository(TwitterApp.getInstance()));
    }

    @Override
    public void showInlineError(String error) {
       DialogUtils.getSnackBar(loginLayout, error, null, null).show();
    }

    @Override
    public void navigateToHomeScreen() {
        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

    @Override
    public void showInlineConnectionError(String error) {
        // no need to implement this method in login fragment
    }

    @Override
    public void showProgressLoading() {
        // no need to implement this method in login fragment
    }

    @Override
    public void hideProgressLoading() {
        // no need to implement this method in login fragment
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.onViewAttached(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        loginPresenter.onViewDetached();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }
}