package com.android.twitterapp.custom.tweetview.listeners;

/**
 * Tweet interaction listener
 */
public interface LinkClickListener {
    /**
     * A URL was clicked
     *
     * @param url The source URL
     */
    public void onUrlClicked(String url);
}
