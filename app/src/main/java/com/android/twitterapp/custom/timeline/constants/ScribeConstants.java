package com.android.twitterapp.custom.timeline.constants;

import com.twitter.sdk.android.core.internal.scribe.EventNamespace;
import com.twitter.sdk.android.core.internal.scribe.SyndicatedSdkImpressionEvent;
import com.twitter.sdk.android.core.internal.scribe.SyndicationClientEvent;

public final class ScribeConstants {
    // tfw client event specific names
    static final String TFW_CLIENT_EVENT_PAGE = "android";

    static final String SYNDICATED_SDK_IMPRESSION_ELEMENT = ""; // intentionally blank

    // general names
    static final String SCRIBE_IMPRESSION_ACTION = "impression";
    static final String SCRIBE_FILTER_ACTION = "filter";
    static final String SCRIBE_INITIAL_ELEMENT = "initial";
    static final String SCRIBE_TIMELINE_SECTION = "timeline";
    static final String SCRIBE_TIMELINE_PAGE = "timeline";
    static final String SCRIBE_INITIAL_COMPONENT = "initial";

    private ScribeConstants() {}

    public static EventNamespace getSyndicatedSdkTimelineNamespace(String timelineType) {
        return new EventNamespace.Builder()
                .setClient(SyndicatedSdkImpressionEvent.CLIENT_NAME)
                .setPage(SCRIBE_TIMELINE_PAGE)
                .setSection(timelineType)
                .setComponent(SCRIBE_INITIAL_COMPONENT)
                .setElement(SYNDICATED_SDK_IMPRESSION_ELEMENT)
                .setAction(SCRIBE_IMPRESSION_ACTION)
                .builder();
    }

    public static EventNamespace getTfwClientTimelineNamespace(String timelineType) {
        return new EventNamespace.Builder()
                .setClient(SyndicationClientEvent.CLIENT_NAME)
                .setPage(TFW_CLIENT_EVENT_PAGE)
                .setSection(SCRIBE_TIMELINE_SECTION)
                .setComponent(timelineType)
                .setElement(SCRIBE_INITIAL_ELEMENT)
                .setAction(SCRIBE_IMPRESSION_ACTION)
                .builder();
    }

    public static EventNamespace getTfwClientFilterTimelineNamespace(String timelineType) {
        return new EventNamespace.Builder()
                .setClient(SyndicationClientEvent.CLIENT_NAME)
                .setPage(TFW_CLIENT_EVENT_PAGE)
                .setSection(SCRIBE_TIMELINE_SECTION)
                .setComponent(timelineType)
                .setElement(SCRIBE_INITIAL_ELEMENT)
                .setAction(SCRIBE_FILTER_ACTION)
                .builder();
    }
}
