package com.android.twitterapp.custom.tweetview.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds values we need to correctly render tweet text. The values returned directly
 * from the REST API are html escaped for & < and > characters as well as not counting emoji
 * characters correctly in the entity indices.
 */
public class FormattedTweetText {
    public String text;
    public final List<FormattedUrlEntity> urlEntities;
    public final List<FormattedMediaEntity> mediaEntities;
    public final List<FormattedUrlEntity> hashtagEntities;
    public final List<FormattedUrlEntity> mentionEntities;
    public final List<FormattedUrlEntity> symbolEntities;

    public FormattedTweetText() {
        urlEntities = new ArrayList<>();
        mediaEntities = new ArrayList<>();
        hashtagEntities = new ArrayList<>();
        mentionEntities = new ArrayList<>();
        symbolEntities = new ArrayList<>();
    }
}
