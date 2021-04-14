package com.productive6.productive.persistence.datamanage.dummy;

import com.productive6.productive.objects.User;
import com.productive6.productive.persistence.room.access.IUserAccess;
import com.productive6.productive.persistence.datamanage.IUserPersistenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A {@link IUserAccess} implementation for unit testing.
 * No actual SQL/room database operations are performed. Everything is kept in an internal cache.
 */
public class DummyUserPersistenceManager implements IUserPersistenceManager {

    /**
     * Internal cache of users to simulate an actual database
     */
    private final List<User> users;

    public DummyUserPersistenceManager() {
        this.users = new ArrayList<>();
    }

    @Override
    public void insertUser(User u, Runnable r) {
        users.add(u);
        u.setId(users.size());
        r.run();
    }

    @Override
    public void getAllUsers(Consumer<List<User>> callback) {
        callback.accept(users);
    }

    @Override
    public void updateUser(User u, Runnable after) {
        //doesn't need to be worried about; Thanks pointers!
        after.run();
    }
}
