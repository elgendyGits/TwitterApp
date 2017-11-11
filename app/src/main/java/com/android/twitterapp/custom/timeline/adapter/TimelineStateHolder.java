package com.android.twitterapp.custom.timeline.adapter;

import com.android.twitterapp.custom.timeline.base.TimelineCursor;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Component which holds a TimelineAdapter's data about whether a request is in flight and the
 * scroll position TimelineCursors.
 */
public class TimelineStateHolder {
    // cursor for Timeline 'next' calls
    TimelineCursor nextCursor;
    // cursor for Timeline 'previous' calls
    TimelineCursor previousCursor;
    // true while a request is in flight, false otherwise
    public final AtomicBoolean requestInFlight = new AtomicBoolean(false);

    public TimelineStateHolder() {
        // intentionally blank
    }

    /* for testing */
    public TimelineStateHolder(TimelineCursor nextCursor, TimelineCursor previousCursor) {
        this.nextCursor = nextCursor;
        this.previousCursor = previousCursor;
    }

    /**
     * Nulls the nextCursor and previousCursor
     */
    public void resetCursors() {
        nextCursor = null;
        previousCursor = null;
    }

    /**
     * Returns the position to use for the subsequent Timeline.next call.
     */
    public Long positionForNext() {
        return nextCursor == null ? null : nextCursor.maxPosition;
    }

    /**
     * Returns the position to use for the subsequent Timeline.previous call.
     */
    public Long positionForPrevious() {
        return previousCursor == null ? null : previousCursor.minPosition;
    }

    /**
     * Updates the nextCursor
     */
    public void setNextCursor(TimelineCursor timelineCursor) {
        nextCursor = timelineCursor;
        setCursorsIfNull(timelineCursor);
    }

    /**
     * Updates the previousCursor.
     */
    public void setPreviousCursor(TimelineCursor timelineCursor) {
        previousCursor = timelineCursor;
        setCursorsIfNull(timelineCursor);
    }

    /**
     * If a nextCursor or previousCursor is null, sets it to timelineCursor. Should be called by
     * setNextCursor and setPreviousCursor to handle the very first timeline load which sets
     * both cursors.
     */
    public void setCursorsIfNull(TimelineCursor timelineCursor) {
        if (nextCursor == null) {
            nextCursor = timelineCursor;
        }
        if (previousCursor == null) {
            previousCursor = timelineCursor;
        }
    }

    /**
     * Returns true if a timeline request is not in flight, false otherwise. If true, a caller
     * must later call finishTimelineRequest to remove the requestInFlight lock.
     */
    public boolean startTimelineRequest() {
        return requestInFlight.compareAndSet(false, true);
    }

    /**
     * Unconditionally sets requestInFlight to false.
     */
    public void finishTimelineRequest() {
        requestInFlight.set(false);
    }
}
