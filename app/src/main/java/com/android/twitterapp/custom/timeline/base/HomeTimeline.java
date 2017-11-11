package com.android.twitterapp.custom.timeline.base;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Mohamed Elgendy.
 */

public class HomeTimeline extends BaseTimeline implements Timeline<Tweet> {
    private static final String SCRIBE_SECTION = "user";

    final TwitterCore twitterCore;
    final Long userId;
    final String screenName;
    final Integer maxItemsPerRequest;
    final Boolean includeReplies;
    final Boolean includeRetweets;

    HomeTimeline(TwitterCore twitterCore, Long userId, String screenName,
                 Integer maxItemsPerRequest, Boolean includeReplies, Boolean includeRetweets) {
        this.twitterCore = twitterCore;
        this.userId = userId;
        this.screenName = screenName;
        this.maxItemsPerRequest = maxItemsPerRequest;
        // null includeReplies should default to false
        this.includeReplies = includeReplies == null ? false : includeReplies;
        this.includeRetweets = includeRetweets;
    }

    /**
     * Loads Tweets with id greater than (newer than) sinceId. If sinceId is null, loads the newest
     * Tweets.
     * @param sinceId minimum id of the Tweets to load (exclusive).
     * @param cb callback.
     */
    @Override
    public void next(Long sinceId, Callback<TimelineResult<Tweet>> cb) {
        createHomeTimelineRequest(sinceId, null).enqueue(new TweetsCallback(cb));
    }

    /**
     * Loads Tweets with id less than (older than) maxId.
     * @param maxId maximum id of the Tweets to load (exclusive).
     * @param cb callback.
     */
    @Override
    public void previous(Long maxId, Callback<TimelineResult<Tweet>> cb) {
        // user timeline api provides results which are inclusive, decrement the maxId to get
        // exclusive results
        createHomeTimelineRequest(null, decrementMaxId(maxId)).enqueue(new TweetsCallback(cb));
    }

    @Override
    public String getTimelineType() {
        return SCRIBE_SECTION;
    }

    Call<List<Tweet>> createHomeTimelineRequest(final Long sinceId, final Long maxId) {
        /*return twitterCore.getApiClient().getStatusesService().userTimeline(userId,
                screenName, maxItemsPerRequest, sinceId, maxId, false, !includeReplies, null,
                includeRetweets);*/

        return twitterCore.getApiClient().getStatusesService().homeTimeline(maxItemsPerRequest,
                null, null, null, null, null, null);
    }

    /**
     * UserTimeline Builder.
     */
    public static class Builder {
        private final TwitterCore twitterCore;
        private Long userId;
        private String screenName;
        private Integer maxItemsPerRequest = 30;
        private Boolean includeReplies;
        private Boolean includeRetweets;

        /**
         * Constructs a Builder.
         */
        public Builder() {
            twitterCore = TwitterCore.getInstance();
        }

        // For testing
        Builder(TwitterCore twitterCore) {
            this.twitterCore = twitterCore;
        }

        /**
         * Sets the userId for the UserTimeline.
         * @param userId The ID of the user for whom to return results for.
         */
        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        /**
         * Sets the screenName for the UserTimeline.
         * @param screenName The screen name of the user for whom to return results for.
         */
        public Builder screenName(String screenName) {
            this.screenName = screenName;
            return this;
        }

        /**
         * Sets the number of Tweets returned per request for the UserTimeline.
         * @param maxItemsPerRequest The number of tweets to return per request, up to a maximum of
         * 200.
         */
        public Builder maxItemsPerRequest(Integer maxItemsPerRequest) {
            this.maxItemsPerRequest = maxItemsPerRequest;
            return this;
        }

        /**
         * Sets whether to includeReplies for the UserTimeline. Defaults to false.
         * @param includeReplies true to allow replies to be included in the returned timeline
         * result.
         */
        public Builder includeReplies(Boolean includeReplies) {
            this.includeReplies = includeReplies;
            return this;
        }

        /**
         * Sets whether to includeRetweets for the UserTimeline. Defaults to true.
         * @param includeRetweets When set to false, the timeline will strip any native retweets
         * (though they will still count toward both the maximal length of the timeline and the
         * slice selected by the count parameter).
         */
        public Builder includeRetweets(Boolean includeRetweets) {
            this.includeRetweets = includeRetweets;
            return this;
        }

        /**
         * Builds a UserTimeline from the Builder parameters.
         * @return a UserTimeline.
         */
        public HomeTimeline build() {
            return new HomeTimeline(twitterCore, userId, screenName, maxItemsPerRequest,
                    includeReplies, includeRetweets);
        }
    }
}
