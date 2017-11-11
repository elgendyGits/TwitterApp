package com.android.twitterapp.custom.timeline.delegates;

import android.os.Handler;
import android.os.Looper;

import com.android.twitterapp.custom.timeline.base.BaseTimeline;
import com.android.twitterapp.custom.timeline.base.Timeline;
import com.android.twitterapp.custom.timeline.base.TimelineCursor;
import com.android.twitterapp.custom.timeline.base.TimelineResult;
import com.android.twitterapp.custom.timeline.constants.ScribeConstants;
import com.android.twitterapp.custom.tweetview.view.TweetUi;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.internal.scribe.ScribeItem;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * FilterTimelineDelegate manages and filters timeline data items and loads items from a Timeline.
 */
public class FilterTimelineDelegate extends TimelineDelegate<Tweet> {
    public final TimelineFilter timelineFilter;
    public final TweetUi tweetUi;

    static final String TWEETS_COUNT_JSON_PROP = "tweet_count";
    static final String TWEETS_FILTERED_JSON_PROP = "tweets_filtered";
    static final String TOTAL_APPLIED_FILTERS_JSON_PROP = "total_filters";
    final Gson gson = new Gson();

    /**
     * Constructs a FilterTimelineDelegate with a timeline for requesting data and timelineFilter to
     * filter tweets
     *
     * @param timeline       Timeline source
     * @param timelineFilter a timelineFilter for filtering tweets from timeline
     * @throws IllegalArgumentException if timeline is null
     */
    public FilterTimelineDelegate(Timeline<Tweet> timeline, TimelineFilter timelineFilter) {
        super(timeline);
        this.timelineFilter = timelineFilter;
        this.tweetUi = TweetUi.getInstance();
    }

    @Override
    public void refresh(Callback<TimelineResult<Tweet>> developerCb) {
        // reset scrollStateHolder cursors to be null, loadNext will get latest items
        timelineStateHolder.resetCursors();
        // load latest timeline items and replace existing items
        loadNext(timelineStateHolder.positionForNext(),
                new TimelineFilterCallback(new RefreshCallback(developerCb, timelineStateHolder),
                        timelineFilter));
    }

    @Override
    public void next(Callback<TimelineResult<Tweet>> developerCb) {
        loadNext(timelineStateHolder.positionForNext(),
                new TimelineFilterCallback(new NextCallback(developerCb, timelineStateHolder),
                        timelineFilter));
    }

    @Override
    public void previous() {
        loadPrevious(timelineStateHolder.positionForPrevious(),
                new TimelineFilterCallback(new PreviousCallback(timelineStateHolder),
                        timelineFilter));
    }

    /**
     * Handles filtering of tweets from the timeline, provided a given TimelineFilter
     */
    class TimelineFilterCallback extends Callback<TimelineResult<Tweet>> {
        final DefaultCallback callback;
        final TimelineFilter timelineFilter;
        final Handler handler;
        final ExecutorService executorService;

        TimelineFilterCallback(DefaultCallback callback, TimelineFilter timelineFilter) {
            this.callback = callback;
            this.timelineFilter = timelineFilter;
            this.handler = new Handler(Looper.getMainLooper());
            this.executorService = Twitter.getInstance().getExecutorService();
        }

        @Override
        public void success(final Result<TimelineResult<Tweet>> result) {
            final Runnable timelineFilterRunnable = new Runnable() {
                @Override
                public void run() {
                    final List<Tweet> filteredTweets = timelineFilter.filter(result.data.items);
                    final TimelineResult<Tweet> filteredTimelineResult =
                            buildTimelineResult(result.data.timelineCursor, filteredTweets);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.success(new Result<>(filteredTimelineResult, result.response));
                        }
                    });

                    scribeFilteredTimeline(result.data.items, filteredTweets);
                }
            };

            executorService.execute(timelineFilterRunnable);
        }

        @Override
        public void failure(final TwitterException ex) {
            if (callback != null) {
                callback.failure(ex);
            }
        }

        TimelineResult<Tweet> buildTimelineResult(TimelineCursor timelineCursor,
                                                  List<Tweet> filteredTweets) {
            return new TimelineResult<>(timelineCursor, filteredTweets);
        }
    }

    void scribeFilteredTimeline(List<Tweet> tweets, List<Tweet> filteredTweets) {
        final int tweetCount = tweets.size();
        final int totalTweetsFiltered = tweetCount - filteredTweets.size();
        final int totalFilters = timelineFilter.totalFilters();

        final String jsonMessage = getJsonMessage(tweetCount, totalTweetsFiltered,
                totalFilters);
        final ScribeItem scribeItem = ScribeItem.fromMessage(jsonMessage);
        final List<ScribeItem> items = new ArrayList<>();
        items.add(scribeItem);

        final String timelineType = getTimelineType(timeline);
        tweetUi.scribe(ScribeConstants.getTfwClientFilterTimelineNamespace(timelineType), items);
    }

    private String getJsonMessage(int totalTweetsSize, int filteredTweetsSize, int totalFilters) {
        final JsonObject message = new JsonObject();
        message.addProperty(TWEETS_COUNT_JSON_PROP, totalTweetsSize);
        message.addProperty(TWEETS_FILTERED_JSON_PROP, totalTweetsSize - filteredTweetsSize);
        message.addProperty(TOTAL_APPLIED_FILTERS_JSON_PROP, totalFilters);
        return gson.toJson(message);
    }

    static String getTimelineType(Timeline timeline) {
        if (timeline instanceof BaseTimeline) {
            return ((BaseTimeline) timeline).getTimelineType();
        }
        return "other";
    }
}