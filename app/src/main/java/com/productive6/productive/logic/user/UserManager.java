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
public interface UserManager {

    /**
     * Get the current (iteration 1: ONLY) user using this application.
     * @param callback output parameter for when the user is found.
     */
    void getCurrentUser(Consumer<User> callback);

    /**
     * Called when any information of user changes, such as xp
     * @param u the user who's information has changed.
     */
    void updateUser(User u);
}
