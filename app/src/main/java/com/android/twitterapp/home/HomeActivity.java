package com.android.twitterapp.home;

import android.os.Bundle;

import com.android.twitterapp.R;
import com.android.twitterapp.base.view.BaseActivity;
import com.android.twitterapp.home.view.HomeFragment;
import com.android.twitterapp.utils.FragmentUtils;

import static com.android.twitterapp.application.TwitterAppConstants.HOME_FRAG_TAG;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //show Home timeline
        FragmentUtils.replaceFragment(this, new HomeFragment(), R.id.fragment_home_container,
                false, HOME_FRAG_TAG);
    }
}
