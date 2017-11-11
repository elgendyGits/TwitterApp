package com.android.twitterapp.custom.tweetview.listeners;

import com.android.twitterapp.custom.tweetview.data.TweetRepository;
import com.android.twitterapp.custom.tweetview.view.BaseTweetView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

/**
 * Handles Tweet successes and failures by setting the new Tweet on the given TweetView and
 * clearing the single Tweet cache. Calls through to the given Callback.
 */
public class ResetTweetCallback extends Callback<Tweet> {
    final BaseTweetView baseTweetView;
    final TweetRepository tweetRepository;
    final Callback<Tweet> cb;

    public ResetTweetCallback(BaseTweetView baseTweetView, TweetRepository tweetRepository,
                       Callback<Tweet> cb) {
        this.baseTweetView = baseTweetView;
        this.tweetRepository = tweetRepository;
        this.cb = cb;
    }

    @Override
    public void success(Result<Tweet> result) {
        tweetRepository.updateCache(result.data);
        baseTweetView.setTweet(result.data);
        if (cb != null) {
            cb.success(result);
        }
    }

    @Override
    public void failure(TwitterException exception) {
        if (cb != null) {
            cb.failure(exception);
        }
    }
}
