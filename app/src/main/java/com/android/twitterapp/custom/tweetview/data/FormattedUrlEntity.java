package com.android.twitterapp.custom.tweetview.data;

import com.android.twitterapp.custom.tweetview.utils.TweetUtils;
import com.twitter.sdk.android.core.models.HashtagEntity;
import com.twitter.sdk.android.core.models.MentionEntity;
import com.twitter.sdk.android.core.models.SymbolEntity;
import com.twitter.sdk.android.core.models.UrlEntity;

public class FormattedUrlEntity {
    public int start;
    public int end;
    public final String displayUrl;
    public final String url;
    public final String expandedUrl;

    public FormattedUrlEntity(int start, int end, String displayUrl, String url, String expandedUrl) {
        this.start = start;
        this.end = end;
        this.displayUrl = displayUrl;
        this.url = url;
        this.expandedUrl = expandedUrl;
    }

    public static FormattedUrlEntity createFormattedUrlEntity(UrlEntity entity) {
        return new FormattedUrlEntity(entity.getStart(), entity.getEnd(), entity.displayUrl,
                entity.url, entity.expandedUrl);
    }

    public static FormattedUrlEntity createFormattedUrlEntity(HashtagEntity hashtagEntity) {
        final String url = TweetUtils.getHashtagPermalink(hashtagEntity.text);
        return new FormattedUrlEntity(hashtagEntity.getStart(), hashtagEntity.getEnd(),
                "#" + hashtagEntity.text, url, url);
    }

    public static FormattedUrlEntity createFormattedUrlEntity(MentionEntity mentionEntity) {
        final String url = TweetUtils.getProfilePermalink(mentionEntity.screenName);
        return new FormattedUrlEntity(mentionEntity.getStart(), mentionEntity.getEnd(),
                "@" + mentionEntity.screenName, url, url);
    }

    public static FormattedUrlEntity createFormattedUrlEntity(SymbolEntity symbolEntity) {
        final String url = TweetUtils.getSymbolPermalink(symbolEntity.text);
        return new FormattedUrlEntity(symbolEntity.getStart(), symbolEntity.getEnd(),
                "$" + symbolEntity.text, url, url);
    }
}
