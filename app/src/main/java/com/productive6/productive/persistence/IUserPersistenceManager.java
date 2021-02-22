package com.productive6.productive.persistence;

import androidx.room.Insert;

import com.productive6.productive.objects.User;

import java.util.List;

/**
 * Interface specifically handling the persistence of users
 */
public interface IUserPersistenceManager {


    /**
     * Inserts a new (no-id)
     * task into the database.
     * An id is automatically added to the object.
     * @param u the task to add.
     */
    void insertUser(User u);

    /**
     * @return a list of all users. Ordering is inspecific.
     */
    List<User> getAllUsers();

    /**
     * Updates the database information of a user
     * which has already been assigned an id
     * @param u the task to update
     */
    void updateUser(User u);
}
