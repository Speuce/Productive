package com.productive6.productive.persistence.datamanage;

import com.productive6.productive.objects.User;

import java.util.List;
import java.util.function.Consumer;

/**
 * An interface for interacting with the data layer with regards to tasks
 * Implementations should be thread safe, and callbacks will be called on the main thread.
 */
public interface IUserPersistenceManager {


    /**
     * Inserts a new (no-id)
     * task into the database.
     * An id is automatically added to the object.
     * @param u the task to add.
     * @param after called after the user has been created.
     *              (ran on main thread)
     */
    void insertUser(User u, Runnable after);

    /**
     * @param callback a list of all users. Ordering is inspecific.
     */
    void getAllUsers(Consumer<List<User>> callback);

    /**
     * Updates the database information of a user
     * which has already been assigned an id
     * @param u the task to update
     * @param after called after the user has been created.
     *              (ran on main thread)
     */
    void updateUser(User u, Runnable after);
}
