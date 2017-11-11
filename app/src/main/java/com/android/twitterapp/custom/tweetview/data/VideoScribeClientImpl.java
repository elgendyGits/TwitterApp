package com.android.twitterapp.custom.tweetview.data;

import com.android.twitterapp.custom.tweetview.view.TweetUi;
import com.twitter.sdk.android.core.internal.scribe.EventNamespace;
import com.twitter.sdk.android.core.internal.scribe.ScribeItem;
import com.twitter.sdk.android.core.internal.scribe.SyndicationClientEvent;
import com.twitter.sdk.android.tweetui.VideoScribeClient;

import java.util.ArrayList;
import java.util.List;

public class VideoScribeClientImpl implements VideoScribeClient {

    static final String TFW_CLIENT_EVENT_PAGE = "android";

    static final String TFW_CLIENT_EVENT_SECTION = "video";

    static final String SCRIBE_IMPRESSION_ACTION = "impression";

    static final String SCRIBE_PLAY_ACTION = "play";

    final TweetUi tweetUi;

    public VideoScribeClientImpl(TweetUi tweetUi) {
        this.tweetUi = tweetUi;
    }

    @Override
    public void impression(ScribeItem scribeItem) {
        final List<ScribeItem> items = new ArrayList<>();
        items.add(scribeItem);

        tweetUi.scribe(getTfwImpressionNamespace(), items);
    }

    @Override
    public void play(ScribeItem scribeItem) {
        final List<ScribeItem> items = new ArrayList<>();
        items.add(scribeItem);

        tweetUi.scribe(getTfwPlayNamespace(), items);
    }

    public static EventNamespace getTfwImpressionNamespace() {
        return new EventNamespace.Builder()
                .setClient(SyndicationClientEvent.CLIENT_NAME)
                .setPage(TFW_CLIENT_EVENT_PAGE)
                .setSection(TFW_CLIENT_EVENT_SECTION)
                .setAction(SCRIBE_IMPRESSION_ACTION)
                .builder();
    }

    public static EventNamespace getTfwPlayNamespace() {
        return new EventNamespace.Builder()
                .setClient(SyndicationClientEvent.CLIENT_NAME)
                .setPage(TFW_CLIENT_EVENT_PAGE)
                .setSection(TFW_CLIENT_EVENT_SECTION)
                .setAction(SCRIBE_PLAY_ACTION)
                .builder();
    }
}

