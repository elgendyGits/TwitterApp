package com.android.twitterapp.home.model.repo;

import com.android.twitterapp.custom.timeline.base.HomeTimeline;

/**
 * Created by Mohamed Elgendy.
 */

public interface HomeDataSource {
    HomeTimeline getTimeline();
}
