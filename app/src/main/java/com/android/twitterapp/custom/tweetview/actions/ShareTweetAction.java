package com.android.twitterapp.custom.tweetview.actions;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;

import com.android.twitterapp.custom.tweetview.data.TweetScribeClientImpl;
import com.android.twitterapp.custom.tweetview.view.TweetUi;
import com.twitter.sdk.android.core.IntentUtils;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetScribeClient;

public class ShareTweetAction implements View.OnClickListener {
    final Tweet tweet;
    final TweetUi tweetUi;
    final TweetScribeClient tweetScribeClient;

    public ShareTweetAction(Tweet tweet, TweetUi tweetUi) {
        this(tweet, tweetUi, new TweetScribeClientImpl(tweetUi));
    }

    // For testing only
    public ShareTweetAction(Tweet tweet, TweetUi tweetUi, TweetScribeClient tweetScribeClient) {
        super();
        this.tweet = tweet;
        this.tweetUi = tweetUi;
        this.tweetScribeClient = tweetScribeClient;
    }

    @Override
    public void onClick(View v) {
        onClick(v.getContext(), v.getResources());
    }

    void scribeShareAction() {
        tweetScribeClient.share(tweet);
    }

    void onClick(Context context, Resources resources) {
        if (tweet == null || tweet.user == null) return;

        scribeShareAction();

        final String shareSubject = getShareSubject(resources);
        final String shareContent = getShareContent(resources);
        final Intent shareIntent = getShareIntent(shareSubject, shareContent);
        final String shareText = resources.getString(com.twitter.sdk.android.tweetui.R.string.tw__share_tweet);
        final Intent chooser = Intent.createChooser(shareIntent, shareText);
        launchShareIntent(chooser, context);
    }

    public String getShareContent(Resources resources) {
        return resources.getString(com.twitter.sdk.android.tweetui.R.string.tw__share_content_format,
                tweet.user.screenName, Long.toString(tweet.id));
    }

    public String getShareSubject(Resources resources) {
        return resources.getString(com.twitter.sdk.android.tweetui.R.string.tw__share_subject_format, tweet.user.name,
                tweet.user.screenName);
    }

    public void launchShareIntent(Intent chooser, Context context) {
        if (!IntentUtils.safeStartActivity(context, chooser)) {
            Twitter.getLogger()
                    .e(TweetUi.LOGTAG, "Activity cannot be found to handle share intent");
        }
    }

    public Intent getShareIntent(String subject, String content) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("text/plain");
        return intent;
    }
}

