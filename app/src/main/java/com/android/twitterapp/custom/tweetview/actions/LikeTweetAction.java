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


/*
 * LikeTweetAction is a click listener for ToggleImageButtons which performs Twitter API
 * favorite/unfavorite actions onClick, updates button state, and calls through to the given
 * callback.
 */
public class LikeTweetAction extends BaseTweetAction implements View.OnClickListener {

    final Tweet tweet;
    final TweetRepository tweetRepository;
    final TweetUi tweetUi;
    final TweetScribeClient tweetScribeClient;

    public LikeTweetAction(Tweet tweet, TweetUi tweetUi, Callback<Tweet> cb) {
        this(tweet, tweetUi, cb, new TweetScribeClientImpl(tweetUi));
    }

    // For testing only
    public LikeTweetAction(Tweet tweet, TweetUi tweetUi, Callback<Tweet> cb,
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
            if (tweet.favorited) {
                scribeUnFavoriteAction();
                tweetRepository.unfavorite(tweet.id,
                        new LikeCallback(toggleImageButton, tweet, getActionCallback()));
            } else {
                scribeFavoriteAction();
                tweetRepository.favorite(tweet.id,
                        new LikeCallback(toggleImageButton, tweet, getActionCallback()));
            }
        }
    }

    public void scribeFavoriteAction() {
        tweetScribeClient.favorite(tweet);
    }

    public void scribeUnFavoriteAction() {
        tweetScribeClient.unfavorite(tweet);
    }

    /*
     * Toggles like button state to handle favorite/unfavorite API exceptions. It calls through to
     * the given action callback.
     */
    public static class LikeCallback extends Callback<Tweet> {
        final ToggleImageButton button;
        final Tweet tweet;
        final Callback<Tweet> cb;

        /*
         * Constructs a new FavoriteCallback.
         * @param button Favorite ToggleImageButton which should reflect Tweet favorited state
         * @param wasFavorited whether the Tweet was favorited or not before the click
         * @param cb the Callback.
         */
        public LikeCallback(ToggleImageButton button, Tweet tweet, Callback<Tweet> cb) {
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

                switch (errorCode) {
                    case TwitterApiConstants.Errors.ALREADY_FAVORITED:
                        final Tweet favorited = new TweetBuilder().copy(tweet).setFavorited(true)
                                .build();
                        cb.success(new Result<>(favorited, null));
                        return;
                    case TwitterApiConstants.Errors.ALREADY_UNFAVORITED:
                        final Tweet unfavorited = new TweetBuilder().copy(tweet).setFavorited(false)
                                .build();
                        cb.success(new Result<>(unfavorited, null));
                        return;
                    default:
                        // reset the toggle state back to match the Tweet
                        button.setToggledOn(tweet.favorited);
                        cb.failure(exception);
                        return;
                }
            }
            // reset the toggle state back to match the Tweet
            button.setToggledOn(tweet.favorited);
            cb.failure(exception);
        }
    }
}

