package com.android.twitterapp.home.presenter;

import com.android.twitterapp.R;
import com.android.twitterapp.application.TwitterApp;
import com.android.twitterapp.base.view.BaseView;
import com.android.twitterapp.home.model.HomeRepository;
import com.android.twitterapp.home.view.HomeView;
import com.android.twitterapp.custom.timeline.base.HomeTimeline;
import com.android.twitterapp.utils.ConnectionUtils;
import com.android.twitterapp.utils.TextUtils;

/**
 * Created by Mohamed Elgendy.
 */

public class HomePresenterImpl implements HomePresenter {

    private HomeView homeView;
    private HomeRepository homeRepository;
    private boolean isViewAttached;

    public HomePresenterImpl(HomeView homeView, HomeRepository homeRepository) {
        this.homeView = homeView;
        this.homeRepository = homeRepository;
    }

    @Override
    public void loadTimeline() {

        if (ConnectionUtils.isConnected(TwitterApp.getInstance())) {
            homeView.showProgressLoading();
            HomeTimeline homeTimeline = homeRepository.getTimeline();
            homeView.setTimeline(homeTimeline);

        } else {
            homeView.showInlineConnectionError(TextUtils.getString(R.string.connection_failed));
        }
    }

    @Override
    public void onViewAttached(BaseView view) {
        isViewAttached = true;
    }

    @Override
    public void onViewDetached() {
        isViewAttached = false;
    }
}
