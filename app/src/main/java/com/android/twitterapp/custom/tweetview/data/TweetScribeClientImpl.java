package com.android.twitterapp.custom.tweetview.data;

import com.android.twitterapp.custom.tweetview.listeners.TweetScribeClient;
import com.android.twitterapp.custom.tweetview.view.TweetUi;
import com.twitter.sdk.android.core.internal.scribe.EventNamespace;
import com.twitter.sdk.android.core.internal.scribe.ScribeItem;
import com.twitter.sdk.android.core.internal.scribe.SyndicatedSdkImpressionEvent;
import com.twitter.sdk.android.core.internal.scribe.SyndicationClientEvent;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetScribeClientImpl implements TweetScribeClient {
    // tfw client event specific names
    static final String TFW_CLIENT_EVENT_PAGE = "android";
    static final String TFW_CLIENT_EVENT_SECTION = "tweet";
    static final String TFW_CLIENT_EVENT_ELEMENT = ""; // intentionally blank

    // syndicated sdk impression specific names
    static final String SYNDICATED_SDK_IMPRESSION_PAGE = "tweet";
    static final String SYNDICATED_SDK_IMPRESSION_COMPONENT = "";
    static final String SYNDICATED_SDK_IMPRESSION_ELEMENT = ""; // intentionally blank

    // general names
    static final String SCRIBE_CLICK_ACTION = "click";
    static final String SCRIBE_IMPRESSION_ACTION = "impression";
    static final String SCRIBE_FAVORITE_ACTION = "favorite";
    static final String SCRIBE_UNFAVORITE_ACTION = "unfavorite";
    static final String SCRIBE_RETWEET_ACTION = "retweet";
    static final String SCRIBE_UNRETWEET_ACTION = "unretweet";
    static final String SCRIBE_SHARE_ACTION = "share";
    static final String SCRIBE_ACTIONS_ELEMENT = "actions";

    final TweetUi tweetUi;

    public TweetScribeClientImpl(TweetUi tweetUi) {
        this.tweetUi = tweetUi;
    }

    @Override
    public void impression(Tweet tweet, String viewName, boolean actionEnabled) {
        final List<ScribeItem> items = new ArrayList<>();
        items.add(ScribeItem.fromTweet(tweet));

        tweetUi.scribe(getTfwImpressionNamespace(viewName, actionEnabled), items);
        tweetUi.scribe(getSyndicatedImpressionNamespace(viewName), items);
    }

    @Override
    public void share(Tweet tweet) {
        final List<ScribeItem> items = new ArrayList<>();
        items.add(ScribeItem.fromTweet(tweet));

        tweetUi.scribe(getTfwShareNamespace(), items);
    }

    @Override
    public void favorite(Tweet tweet) {
        final List<ScribeItem> items = new ArrayList<>();
        items.add(ScribeItem.fromTweet(tweet));

        tweetUi.scribe(getTfwFavoriteNamespace(), items);
    }

    @Override
    public void unfavorite(Tweet tweet) {
        final List<ScribeItem> items = new ArrayList<>();
        items.add(ScribeItem.fromTweet(tweet));

        tweetUi.scribe(getTfwUnfavoriteNamespace(), items);
    }

    @Override
    public void retweet(Tweet tweet) {
        final List<ScribeItem> items = new ArrayList<>();
        items.add(ScribeItem.fromTweet(tweet));

        tweetUi.scribe(getTfwRetweetNamespace(), items);
    }

    @Override
    public void unretweet(Tweet tweet) {
        final List<ScribeItem> items = new ArrayList<>();
        items.add(ScribeItem.fromTweet(tweet));

        tweetUi.scribe(getTfwUnretweetNamespace(), items);
    }

    @Override
    public void click(Tweet tweet, String viewName) {
        final List<ScribeItem> items = new ArrayList<>();
        items.add(ScribeItem.fromTweet(tweet));

        tweetUi.scribe(getTfwClickNamespace(viewName), items);
    }

    public static EventNamespace getTfwImpressionNamespace(String viewName, boolean actionEnabled) {
        return new EventNamespace.Builder()
                .setClient(SyndicationClientEvent.CLIENT_NAME)
                .setPage(TFW_CLIENT_EVENT_PAGE)
                .setSection(TFW_CLIENT_EVENT_SECTION)
                .setComponent(viewName)
                .setElement(actionEnabled ? SCRIBE_ACTIONS_ELEMENT : TFW_CLIENT_EVENT_ELEMENT)
                .setAction(SCRIBE_IMPRESSION_ACTION)
                .builder();
    }

    public static EventNamespace getTfwUnfavoriteNamespace() {
        return new EventNamespace.Builder()
                .setClient(SyndicationClientEvent.CLIENT_NAME)
                .setPage(TFW_CLIENT_EVENT_PAGE)
                .setSection(TFW_CLIENT_EVENT_SECTION)
                .setElement(SCRIBE_ACTIONS_ELEMENT)
                .setAction(SCRIBE_UNFAVORITE_ACTION)
                .builder();
    }

    public static EventNamespace getTfwFavoriteNamespace() {
        return new EventNamespace.Builder()
                .setClient(SyndicationClientEvent.CLIENT_NAME)
                .setPage(TFW_CLIENT_EVENT_PAGE)
                .setSection(TFW_CLIENT_EVENT_SECTION)
                .setElement(SCRIBE_ACTIONS_ELEMENT)
                .setAction(SCRIBE_FAVORITE_ACTION)
                .builder();
    }

    public static EventNamespace getTfwUnretweetNamespace() {
        return new EventNamespace.Builder()
                .setClient(SyndicationClientEvent.CLIENT_NAME)
                .setPage(TFW_CLIENT_EVENT_PAGE)
                .setSection(TFW_CLIENT_EVENT_SECTION)
                .setElement(SCRIBE_ACTIONS_ELEMENT)
                .setAction(SCRIBE_UNRETWEET_ACTION)
                .builder();
    }

    public static EventNamespace getTfwRetweetNamespace() {
        return new EventNamespace.Builder()
                .setClient(SyndicationClientEvent.CLIENT_NAME)
                .setPage(TFW_CLIENT_EVENT_PAGE)
                .setSection(TFW_CLIENT_EVENT_SECTION)
                .setElement(SCRIBE_ACTIONS_ELEMENT)
                .setAction(SCRIBE_RETWEET_ACTION)
                .builder();
    }

    public static EventNamespace getTfwShareNamespace() {
        return new EventNamespace.Builder()
                .setClient(SyndicationClientEvent.CLIENT_NAME)
                .setPage(TFW_CLIENT_EVENT_PAGE)
                .setSection(TFW_CLIENT_EVENT_SECTION)
                .setElement(SCRIBE_ACTIONS_ELEMENT)
                .setAction(SCRIBE_SHARE_ACTION)
                .builder();
    }

    public static EventNamespace getTfwClickNamespace(String viewName) {
        return new EventNamespace.Builder()
                .setClient(SyndicationClientEvent.CLIENT_NAME)
                .setPage(TFW_CLIENT_EVENT_PAGE)
                .setSection(TFW_CLIENT_EVENT_SECTION)
                .setComponent(viewName)
                .setElement(TFW_CLIENT_EVENT_ELEMENT)
                .setAction(SCRIBE_CLICK_ACTION)
                .builder();
    }

    public static EventNamespace getSyndicatedImpressionNamespace(String viewName) {
        return new EventNamespace.Builder()
                .setClient(SyndicatedSdkImpressionEvent.CLIENT_NAME)
                .setPage(SYNDICATED_SDK_IMPRESSION_PAGE)
                .setSection(viewName)
                .setComponent(SYNDICATED_SDK_IMPRESSION_COMPONENT)
                .setElement(SYNDICATED_SDK_IMPRESSION_ELEMENT)
                .setAction(SCRIBE_IMPRESSION_ACTION)
                .builder();
    }
}

