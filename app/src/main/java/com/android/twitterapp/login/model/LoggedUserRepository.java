package com.android.twitterapp.login.model;

import com.android.twitterapp.login.model.repo.LoggedUserDataSource;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by Mohamed Elgendy.
 */

public class LoggedUserRepository implements LoggedUserDataSource {

    private static LoggedUserRepository INSTANCE = null;
    private final LoggedUserDataSource loggedUserLocalDataSource;

    // Prevent direct instantiation.
    private LoggedUserRepository(LoggedUserDataSource loggedUserLocalDataSource) {
        this.loggedUserLocalDataSource = loggedUserLocalDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * * @param LoggedUserDataSource the device storage data source
     * @return the {@link LoggedUserRepository} instance
     */
    public static LoggedUserRepository getInstance(LoggedUserDataSource loggedUserLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new LoggedUserRepository(loggedUserLocalDataSource);
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
    public void saveUserSession(TwitterSession session) {
        loggedUserLocalDataSource.saveUserSession(session);
    }
}
