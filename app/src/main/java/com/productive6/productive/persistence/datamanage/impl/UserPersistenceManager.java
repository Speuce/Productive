package com.productive6.productive.persistence.datamanage.impl;

import com.productive6.productive.services.executor.IRunnableExecutor;
import com.productive6.productive.objects.User;
import com.productive6.productive.persistence.room.access.IUserAccess;
import com.productive6.productive.persistence.datamanage.IUserPersistenceManager;

import java.util.List;
import java.util.function.Consumer;

/**
 * Thread-safe implementation of the {@link IUserPersistenceManager} interface.
 * Essentailly a thread safe wrapper for the given {@link IUserAccess}
 */
public class UserPersistenceManager implements IUserPersistenceManager {

    /**
     * For accessing different threads
     */
    private final IRunnableExecutor executor;

    /**
     * For accessing the actual database.
     */
    private final IUserAccess access;

    public UserPersistenceManager(IRunnableExecutor executor, IUserAccess access) {
        this.executor = executor;
        this.access = access;
    }

    @Override
    public void insertUser(final User u, final Runnable after) {
        executor.runASync(() -> {
            u.setId(access.insertUser(u));
            executor.runSync(after);
        });
    }

    @Override
    public void getAllUsers(Consumer<List<User>> callback) {
        executor.runASync(() -> {
            List<User> users = access.getAllUsers();
            executor.runSync(() -> callback.accept(users));
        });
    }

    @Override
    public void updateUser(final User u, final Runnable after) {
        executor.runASync(() -> {
            access.updateUser(u);
            executor.runSync(after);});
    }
}
