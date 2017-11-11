package com.android.twitterapp.custom.tweetview.listeners;

import com.twitter.sdk.android.core.models.Tweet;

/**
 * Created by Mohamed Elgendy.
 */

public interface TweetScribeClient {

    void impression(Tweet tweet, String viewName, boolean actionEnabled);

    void share(Tweet tweet);

    void favorite(Tweet tweet);

    void unfavorite(Tweet tweet);

    void retweet(Tweet tweet);

    void unretweet(Tweet tweet);

    void click(Tweet tweet, String viewName);
}
