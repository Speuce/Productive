package com.productive6.productive.logic.user;

import com.productive6.productive.objects.User;

import java.util.function.Consumer;

/**
 * Interface for interacting with the logic level of users
 *
 * Implementing classes will handle verification and validation
 *
 *
 */
public interface IUserManager {

    /**
     * Get the current (iteration 1: ONLY) user using this application.
     * @return the current user
     *
     * WARNING: only call this after usermanager has been loaded.
     * Listen to {}
     */
    User getCurrentUser();

    /**
     * Called when any information of user changes, such as xp
     * @param u the user who's information has changed.
     */
    void updateUser(User u);

    /**
    * Reads data from the data layer and populates local variables.
    */
    void load();
}
