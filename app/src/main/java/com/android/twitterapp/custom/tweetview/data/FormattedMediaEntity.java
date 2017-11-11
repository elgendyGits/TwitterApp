package com.android.twitterapp.custom.tweetview.data;

import com.twitter.sdk.android.core.models.MediaEntity;

public class FormattedMediaEntity extends FormattedUrlEntity {
    final String type;
    final String mediaUrlHttps;

    public FormattedMediaEntity(MediaEntity entity) {
        super(entity.getStart(), entity.getEnd(), entity.displayUrl, entity.url,
                entity.expandedUrl);
        this.type = entity.type;
        this.mediaUrlHttps = entity.mediaUrlHttps;
    }
}

