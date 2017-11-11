package com.android.twitterapp.home.view;

import com.android.twitterapp.base.view.BaseView;
import com.android.twitterapp.custom.timeline.base.HomeTimeline;

/**
 * Created by Mohamed Elgendy.
 */

public interface HomeView extends BaseView {
    void setTimeline(HomeTimeline homeTimeline);
}
