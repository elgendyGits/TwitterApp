package com.android.twitterapp.home.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.twitterapp.R;
import com.android.twitterapp.base.injector.Injection;
import com.android.twitterapp.base.view.BaseFragment;
import com.android.twitterapp.home.presenter.HomePresenter;
import com.android.twitterapp.home.presenter.HomePresenterImpl;
import com.android.twitterapp.custom.timeline.base.HomeTimeline;
import com.android.twitterapp.custom.timeline.adapter.TweetTimelineRecyclerViewAdapter;
import com.android.twitterapp.utils.DialogUtils;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed Elgendy.
 */

public class HomeFragment extends BaseFragment implements HomeView {

    @BindView(R.id.home_timeline_recycler_view)
    RecyclerView timelineRecyclerView;

    @BindView(R.id.progressBar_loading_indicator)
    ProgressBar mLoadingIndicator;

    // declare timeline list components
    private TweetTimelineRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    // declare customer presenter
    private HomePresenter homePresenter;
    private Callback<Tweet> actionCallback;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // initialize views
        layoutManager = new LinearLayoutManager(getActivity());
        timelineRecyclerView.setLayoutManager(layoutManager);

        initializePresenter();
    }

    private void initializePresenter() {

        homePresenter = new HomePresenterImpl(this, Injection.provideHomeRepository());
        homePresenter.loadTimeline();
    }

    @Override
    public void setTimeline(HomeTimeline homeTimeline) {

        hideProgressLoading();

        adapter = new TweetTimelineRecyclerViewAdapter.Builder(getActivity())
                .setTimeline(homeTimeline)
                .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                .setOnActionCallback(getActionCallback())
                .build();

        timelineRecyclerView.setAdapter(adapter);
    }

    public Callback<Tweet> getActionCallback() {

        actionCallback = new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                // Intentionally blank
                //Toast.makeText(getActivity(), "Act", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(TwitterException exception) {
                if (exception instanceof TwitterAuthException) {
                    // startActivity(TwitterCoreMainActivity.newIntent(getActivity()));
                }
            }
        };

        return actionCallback;
    }

    @Override
    public void showProgressLoading() {
        timelineRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressLoading() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        timelineRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showInlineError(String error) {
        DialogUtils.getSnackBar(getView(), error, null, null).show();
    }

    @Override
    public void showInlineConnectionError(String error) {
        Snackbar.make(getView(), error
                , Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        homePresenter.loadTimeline();
                    }
                })
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        homePresenter.onViewAttached(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        homePresenter.onViewDetached();
    }
}
