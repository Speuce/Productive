package com.productive6.productive.persistence.datamanage.impl;

import com.productive6.productive.executor.RunnableExecutor;
import com.productive6.productive.objects.Task;
import com.productive6.productive.persistence.access.ITaskAccess;
import com.productive6.productive.persistence.datamanage.ITaskPersistenceManager;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * Thread-safe implementation of the {@link com.productive6.productive.persistence.datamanage.ITaskPersistenceManager} interface.
 * Essentailly a thread safe wrapper for the given {@link ITaskAccess}
 */
public class TaskPersistenceManager implements ITaskPersistenceManager {

    /**
     * For accessing different threads
     */
    private final RunnableExecutor executor;

    /**
     * For accessing the actual database.
     */
    private final ITaskAccess access;

    public TaskPersistenceManager(RunnableExecutor executor, ITaskAccess access) {
        this.executor = executor;
        this.access = access;
    }

    @Override
    public void insertTask(final Task t) {
        executor.runASync(() ->access.insertTask(t));
    }

    @Override
    public void getAllTasks(final Consumer<List<Task>> callback) {
        executor.runASync(() -> {
            List<Task> tasks = access.getAllTasks();
            executor.runSync(() -> callback.accept(tasks));
        });
    }

    @Override
    public void getAllTasks(final boolean complete, final Consumer<List<Task>> callback) {
        executor.runASync(() -> {
            List<Task> tasks = access.getAllTasks(complete);
            executor.runSync(() -> callback.accept(tasks));
        });
    }

    @Override
    public void updateTask(final Task t) {
        executor.runASync(() ->access.updateTask(t));
    }
}
