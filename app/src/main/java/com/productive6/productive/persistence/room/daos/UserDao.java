package com.productive6.productive.persistence.room.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.productive6.productive.objects.User;
import com.productive6.productive.persistence.room.access.IUserAccess;

import java.util.List;

@Dao
public interface UserDao extends IUserAccess {

    /**
     * Inserts a new (no-id)
     * task into the database.
     * @param u the task to add.
     * @return the id generated for the user
     */
    @Insert
    long insertUser(User u);

    /**
     * @return a list of all users. Ordering is inspecific.
     */
    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    /**
     * Updates the database information of a user
     * which has already been assigned an id
     * @param u the task to update
     */
    @Update
    void updateUser(User u);
}
