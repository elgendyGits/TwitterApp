package com.android.twitterapp.base.presenter;

import com.android.twitterapp.base.view.BaseView;

/**
 * Created by Mohamed Elgendy.
 */

public interface BasePresenter {
    void onViewAttached(BaseView view);
    void onViewDetached();
}
