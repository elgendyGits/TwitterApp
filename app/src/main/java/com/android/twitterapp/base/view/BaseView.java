package com.android.twitterapp.base.view;

/**
 * Created by Mohamed Elgendy.
 */

public interface BaseView {

    void showProgressLoading();
    void hideProgressLoading();
    void showInlineError(String error);
    void showInlineConnectionError(String error);
}