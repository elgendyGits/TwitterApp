package com.android.twitterapp.custom.timeline.base;

import java.util.List;

/**
 * TimelineResult represents timeline items and the TimelineCursor from a Timeline response.
 * @param <T> timeline item type
 */
public class TimelineResult<T> {

    public final TimelineCursor timelineCursor;
    public final List<T> items;

    /**
     * Constructs a TimelineResult storing item and cursor data.
     * @param timelineCursor cursor representing position and containsLastItem data
     * @param items timeline items
     */
    public TimelineResult(TimelineCursor timelineCursor, List<T> items) {
        this.timelineCursor = timelineCursor;
        this.items = items;
    }
}
