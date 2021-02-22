package com.productive6.productive.persistence.dummy;

import com.productive6.productive.objects.User;
import com.productive6.productive.persistence.access.IUserAccess;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link IUserAccess} implementation for unit testing.
 * No actual SQL/room database operations are performed. Everything is kept in an internal cache.
 */
public class DummyUserPersistenceManager implements IUserAccess {

    /**
     * Internal cache of users to simulate an actual database
     */
    private final List<User> users;

    public DummyUserPersistenceManager() {
        this.users = new ArrayList<>();
    }

    @Override
    public void insertUser(User u) {
        u.setId(users.size());
        users.add(u);
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public void updateUser(User u) {
        //doesn't need to be worried about; Thanks pointers!
    }
}
