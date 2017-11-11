package com.android.twitterapp.custom.tweetview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.android.twitterapp.custom.tweetview.data.TweetRepository;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.GuestSessionProvider;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.internal.scribe.DefaultScribeClient;
import com.twitter.sdk.android.core.internal.scribe.EventNamespace;
import com.twitter.sdk.android.core.internal.scribe.ScribeConfig;
import com.twitter.sdk.android.core.internal.scribe.ScribeItem;
import com.twitter.sdk.android.tweetui.BuildConfig;

import java.util.List;

/**
 * The TweetUi Kit provides views to render Tweets.
 */
public class TweetUi {
    @SuppressLint("StaticFieldLeak")
    static volatile TweetUi instance;
    public static final String LOGTAG = "TweetUi";

    private static final String KIT_SCRIBE_NAME = "TweetUi";

    SessionManager<TwitterSession> sessionManager;
    GuestSessionProvider guestSessionProvider;
    DefaultScribeClient scribeClient;
    Context context;

    private TweetRepository tweetRepository;
    private Picasso imageLoader;

    public static TweetUi getInstance() {
        if (instance == null) {
            synchronized (com.twitter.sdk.android.tweetui.TweetUi.class) {
                if (instance == null) {
                    instance = new TweetUi();
                }
            }
        }
        return instance;
    }

    TweetUi() {
        final TwitterCore twitterCore = TwitterCore.getInstance();

        context = Twitter.getInstance().getContext(getIdentifier());
        sessionManager = twitterCore.getSessionManager();
        guestSessionProvider = twitterCore.getGuestSessionProvider();
        tweetRepository = new TweetRepository(new Handler(Looper.getMainLooper()),
                twitterCore.getSessionManager());
        imageLoader = Picasso.with(Twitter.getInstance().getContext(getIdentifier()));
        setUpScribeClient();
    }

    public String getIdentifier() {
        return BuildConfig.GROUP + ":" + BuildConfig.ARTIFACT_ID;
    }

    public String getVersion() {
        return BuildConfig.VERSION_NAME + "." + BuildConfig.BUILD_NUMBER;
    }

    private void setUpScribeClient() {
        final ScribeConfig config =
                DefaultScribeClient.getScribeConfig(KIT_SCRIBE_NAME, getVersion());
        scribeClient = new DefaultScribeClient(context, sessionManager,
                guestSessionProvider, Twitter.getInstance().getIdManager(), config);
    }

    public void scribe(EventNamespace... namespaces) {
        if (scribeClient == null) return;

        for (EventNamespace ns : namespaces) {
            scribeClient.scribe(ns);
        }
    }

    public void scribe(EventNamespace ns, List<ScribeItem> items) {
        if (scribeClient == null) return;

        scribeClient.scribe(ns, items);
    }

    public TweetRepository getTweetRepository() {
        return tweetRepository;
    }

    // Testing purposes only
    void setTweetRepository(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public Picasso getImageLoader() {
        return imageLoader;
    }

    // Testing purposes only
    void setImageLoader(Picasso imageLoader) {
        this.imageLoader = imageLoader;
    }
}
