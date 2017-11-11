package com.android.twitterapp.home.model;

import com.android.twitterapp.home.model.repo.HomeDataSource;
import com.android.twitterapp.custom.timeline.base.HomeTimeline;

/**
 * Created by Mohamed Elgendy.
 */

public class HomeRepository implements HomeDataSource {

    private static HomeRepository INSTANCE = null;

    private final HomeDataSource homeRemoteDataSource;

    // Prevent direct instantiation.
    private HomeRepository(HomeDataSource homeRemoteDataSource) {
        this.homeRemoteDataSource = homeRemoteDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param homeRemoteDataSource the backend data source
     * @return the {@link HomeRepository} instance
     */
    public static HomeRepository getInstance(HomeDataSource homeRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new HomeRepository(homeRemoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force create a new instance
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public HomeTimeline getTimeline() {
        return homeRemoteDataSource.getTimeline();
    }
}

