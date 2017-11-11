package com.android.twitterapp.custom.timeline.base;

import com.twitter.sdk.android.core.Callback;

/**
 * Abstracts a source of items which are bi-directionally traversable (next and previous).
 * @param <T> timeline item type
 */
public interface Timeline<T> {

    /**
     * Loads items with position greater than (above) minPosition. If minPosition is null, loads
     * the newest items.
     * @param minPosition minimum position of the items to load (exclusive).
     * @param cb callback.
     */
    void next(Long minPosition, final Callback<TimelineResult<T>> cb);

    /**
     * Loads items with position less than (below) maxId.
     * @param maxPosition maximum position of the items to load (exclusive).
     * @param cb callback.
     */
    void previous(Long maxPosition, final Callback<TimelineResult<T>> cb);
}

