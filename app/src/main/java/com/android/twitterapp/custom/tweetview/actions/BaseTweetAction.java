package com.android.twitterapp.custom.tweetview.actions;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Tweet;

/**
 * BaseTweetAction keeps a Callback which should be called after Tweet actions are performed.
 */
class BaseTweetAction {
    protected final Callback<Tweet> actionCallback;

    BaseTweetAction(Callback<Tweet> actionCallback) {
        this.actionCallback = actionCallback;
    }

    Callback<Tweet> getActionCallback() {
        return actionCallback;
    }
}
