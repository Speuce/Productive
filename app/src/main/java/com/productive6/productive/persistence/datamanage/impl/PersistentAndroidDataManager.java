package com.productive6.productive.persistence.datamanage.impl;

import android.content.Context;

import androidx.room.Room;

import com.productive6.productive.persistence.executor.IRunnableExecutor;
import com.productive6.productive.objects.Task;
import com.productive6.productive.persistence.ProductiveDB;
import com.productive6.productive.persistence.access.ITaskAccess;
import com.productive6.productive.persistence.access.IUserAccess;
import com.productive6.productive.persistence.datamanage.IDataManager;
import com.productive6.productive.persistence.datamanage.ITaskPersistenceManager;
import com.productive6.productive.persistence.datamanage.IUserPersistenceManager;

import javax.inject.Inject;

/**
 * A production-grade {@link IDataManager} implementation that persists data using a database
 * saved locally on the device.
 */
public class PersistentAndroidDataManager implements IDataManager {

    /**
     * A reference to the actual app object
     * provided by android
     */
    private final Context context;

    private ProductiveDB db;

    private ITaskPersistenceManager taskPersistenceManager;

    private IUserPersistenceManager userPersistenceManager;

    private final IRunnableExecutor executor;

    @Inject
    public PersistentAndroidDataManager(Context context, IRunnableExecutor executor) {
        this.context = context;
        this.executor = executor;

    }

    @Override
    public void init() {
        db = Room.databaseBuilder(context,
                ProductiveDB.class, "productive.db").build();
        this.taskPersistenceManager = new TaskPersistenceManager(executor, db.getTaskDao());
        this.userPersistenceManager = new UserPersistenceManager(executor, db.getUserDao());
    }

    @Override
    public ITaskPersistenceManager task() {
        return taskPersistenceManager;
    }

    @Override
    public IUserPersistenceManager user() {
        return userPersistenceManager;
    }


}
