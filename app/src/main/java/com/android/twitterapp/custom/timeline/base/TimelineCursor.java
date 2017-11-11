package com.android.twitterapp.custom.timeline.base;

import com.twitter.sdk.android.core.models.Identifiable;

import java.util.List;

/**
 * TimelineCursor represents the position and containsLastItem data from a Timeline response.
 */
public class TimelineCursor {
    public final Long minPosition;
    public final Long maxPosition;

    /**
     * Constructs a TimelineCursor storing position and containsLastItem data.
     * @param minPosition the minimum position of items received or Null
     * @param maxPosition the maximum position of items received or Null
     */
    public TimelineCursor(Long minPosition, Long maxPosition) {
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
    }

    /**
     * Constructs a TimelineCursor by reading the maxPosition from the start item and the
     * minPosition from the last item.
     * @param items items from the maxPosition item to the minPosition item
     */
    TimelineCursor(List<? extends Identifiable> items) {
        this.minPosition = items.size() > 0 ? items.get(items.size() - 1).getId() : null;
        this.maxPosition = items.size() > 0 ? items.get(0).getId() : null;
    }
}
