package com.android.twitterapp.custom.tweetview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.android.twitterapp.R;
import com.android.twitterapp.custom.tweetview.actions.LikeTweetAction;
import com.android.twitterapp.custom.tweetview.actions.ShareTweetAction;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.ToggleImageButton;

/**
 * Created by Mohamed Elgendy.
 */

public class TweetActionBarView extends LinearLayout {

    final DependencyProvider dependencyProvider;
    ToggleImageButton likeButton;
    ImageButton shareButton;
    Callback<Tweet> actionCallback;

    public TweetActionBarView(Context context) {
        this(context, null, new DependencyProvider());
    }

    public TweetActionBarView(Context context, AttributeSet attrs) {
        this(context, attrs, new DependencyProvider());
    }

    TweetActionBarView(Context context, AttributeSet attrs, DependencyProvider dependencyProvider) {
        super(context, attrs);
        this.dependencyProvider = dependencyProvider;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findSubviews();
    }

    /*
     * Sets the callback to call when a Tweet Action (favorite, unfavorite) is performed.
     */
    void setOnActionCallback(Callback<Tweet> actionCallback) {
        this.actionCallback = actionCallback;
    }

    void findSubviews() {
        likeButton = (ToggleImageButton) findViewById(R.id.tw__custom_tweet_like_button);
        shareButton = (ImageButton) findViewById(R.id.tw__custom_tweet_share_button);
    }

    /*
     * Setup action bar buttons with Tweet and action performer.
     * @param tweet Tweet source for whether an action has been performed (e.g. isFavorited?)
     */
    void setTweet(Tweet tweet) {
        setLike(tweet);
        setShare(tweet);
    }

    void setLike(Tweet tweet) {
        final TweetUi tweetUi = dependencyProvider.getTweetUi();
        if (tweet != null) {
            likeButton.setToggledOn(tweet.favorited);
            final LikeTweetAction likeTweetAction = new LikeTweetAction(tweet,
                    tweetUi, actionCallback);
            likeButton.setOnClickListener(likeTweetAction);
        }
    }

    void setShare(Tweet tweet) {
        final TweetUi tweetUi = dependencyProvider.getTweetUi();
        if (tweet != null) {
            shareButton.setOnClickListener(new ShareTweetAction(tweet, tweetUi));
        }
    }

    /**
     * This is a mockable class that extracts our tight coupling with the TweetUi singleton.
     */
    static class DependencyProvider {
        /**
         * Return TweetRepository
         */
        TweetUi getTweetUi() {
            return TweetUi.getInstance();
        }
    }
}
