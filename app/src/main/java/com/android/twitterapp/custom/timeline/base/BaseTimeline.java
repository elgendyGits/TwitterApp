package com.android.twitterapp.custom.timeline.base;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * BaseTimeline which handles TweetUi instance argument.
 */
public abstract class BaseTimeline {

    public abstract String getTimelineType();

    /**
     * Returns a decremented maxId if the given id is non-null. Otherwise returns the given maxId.
     * Suitable for REST Timeline endpoints which return inclusive previous results when exclusive
     * is desired.
     */
    public static Long decrementMaxId(Long maxId) {
        return maxId == null ? null : maxId - 1;
    }

    /**
     * Wrapper callback which unpacks a list of Tweets into a TimelineResult (cursor and items).
     */
    public static class TweetsCallback extends Callback<List<Tweet>> {
        final Callback<TimelineResult<Tweet>> cb;

        /**
         * Constructs a TweetsCallback
         * @param cb A callback which expects a TimelineResult
         */
        public TweetsCallback(Callback<TimelineResult<Tweet>> cb) {
            this.cb = cb;
        }

        @Override
        public void success(Result<List<Tweet>> result) {
            final List<Tweet> tweets = result.data;
            final TimelineResult<Tweet> timelineResult
                    = new TimelineResult<>(new TimelineCursor(tweets), tweets);
            if (cb != null) {
                cb.success(new Result<>(timelineResult, result.response));
            }
        }

        @Override
        public void failure(TwitterException exception) {
            if (cb != null) {
                cb.failure(exception);
            }
        }
    }
}

