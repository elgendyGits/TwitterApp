package com.android.twitterapp.custom.tweetview.actions;

import android.view.View;

import com.android.twitterapp.custom.tweetview.data.TweetRepository;
import com.android.twitterapp.custom.tweetview.data.TweetScribeClientImpl;
import com.android.twitterapp.custom.tweetview.listeners.TweetScribeClient;
import com.android.twitterapp.custom.tweetview.view.TweetUi;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiException;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.internal.TwitterApiConstants;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;
import com.twitter.sdk.android.tweetui.ToggleImageButton;

/**
 * Created by Mohamed Elgendy.
 */

public class RetweetedAction extends BaseTweetAction implements View.OnClickListener {

    final Tweet tweet;
    final TweetRepository tweetRepository;
    final TweetUi tweetUi;
    final TweetScribeClient tweetScribeClient;

    public RetweetedAction(Tweet tweet, TweetUi tweetUi, Callback<Tweet> cb) {
        this(tweet, tweetUi, cb, new TweetScribeClientImpl(tweetUi));
    }

    // For testing only
    public RetweetedAction(Tweet tweet, TweetUi tweetUi, Callback<Tweet> cb,
                           TweetScribeClient tweetScribeClient) {
        super(cb);
        this.tweet = tweet;
        this.tweetUi = tweetUi;
        this.tweetScribeClient = tweetScribeClient;
        this.tweetRepository = tweetUi.getTweetRepository();
    }

    @Override
    public void onClick(View view) {
        if (view instanceof ToggleImageButton) {
            final ToggleImageButton toggleImageButton = (ToggleImageButton) view;
            if (tweet.retweeted) {

                scribeUnRetweetAction();
                tweetRepository.unretweet(tweet.id,
                        new RetweetedAction.RetweetCallback(toggleImageButton, tweet, getActionCallback()));
            } else {

                scribeRetweetAction();
                tweetRepository.retweet(tweet.id,
                        new RetweetedAction.RetweetCallback(toggleImageButton, tweet, getActionCallback()));
            }
        }
    }

    public void scribeRetweetAction() {
        tweetScribeClient.retweet(tweet);
    }

    public void scribeUnRetweetAction() {
        tweetScribeClient.unretweet(tweet);
    }

    /*
     * Toggles retweet button state to handle retweet/unretweet API exceptions. It calls through to
     * the given action callback.
     */
    public static class RetweetCallback extends Callback<Tweet> {
        final ToggleImageButton button;
        final Tweet tweet;
        final Callback<Tweet> cb;

        /*
         * Constructs a new RetweetCallback.
         * @param button Retweet ToggleImageButton which should reflect Tweet retweet state
         * @param wasRetweeted whether the Tweet was retweed or not before the click
         * @param cb the Callback.
         */
        public RetweetCallback(ToggleImageButton button, Tweet tweet, Callback<Tweet> cb) {
            this.button = button;
            this.tweet = tweet;
            this.cb = cb;
        }

        @Override
        public void success(Result<Tweet> result) {
            cb.success(result);
        }

        @Override
        public void failure(TwitterException exception) {
            if (exception instanceof TwitterApiException) {
                final TwitterApiException apiException = (TwitterApiException) exception;
                final int errorCode = apiException.getErrorCode();

                // need to check error codes first
            }
            // reset the toggle state back to match the Tweet
            button.setToggledOn(tweet.retweeted);
            cb.failure(exception);
        }
    }
}


