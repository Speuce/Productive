package com.productive6.productive.persistence.datamanage.impl;

import com.productive6.productive.executor.RunnableExecutor;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.User;
import com.productive6.productive.persistence.access.ITaskAccess;
import com.productive6.productive.persistence.access.IUserAccess;
import com.productive6.productive.persistence.datamanage.ITaskPersistenceManager;
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
    private final RunnableExecutor executor;

    /**
     * For accessing the actual database.
     */
    private final IUserAccess access;

    public UserPersistenceManager(RunnableExecutor executor, IUserAccess access) {
        this.executor = executor;
        this.access = access;
    }

    @Override
    public void insertUser(final User u) {
        executor.runASync(() -> access.insertUser(u));
    }

    @Override
    public void getAllUsers(Consumer<List<User>> callback) {
        executor.runASync(() -> {
            List<User> users = access.getAllUsers();
            executor.runSync(() -> callback.accept(users));
        });
    }

    @Override
    public void updateUser(final User u) {
        executor.runASync(() -> access.updateUser(u));
    }
}