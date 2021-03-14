package com.productive6.productive.persistence.datamanage.impl;


import android.content.Context;

import androidx.room.Room;

import com.productive6.productive.persistence.datamanage.IStatisticsDataManager;
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
 * A production-grade {@link IDataManager} implementation.
 */
public abstract class LiveDataManager implements IDataManager {

    protected ProductiveDB db;

    /**
     * A reference to the actual app object
     * provided by android
     */
    protected Context context;

    private ITaskPersistenceManager taskPersistenceManager;

    private IUserPersistenceManager userPersistenceManager;

    private IStatisticsDataManager statisticsDataManager;


    private final IRunnableExecutor executor;

    @Inject
    public LiveDataManager(Context context, IRunnableExecutor executor) {
        this.context = context;
        this.executor = executor;
    }

    @Override
    public void init() {
        this.taskPersistenceManager = new TaskPersistenceManager(executor, db.getTaskDao());
        this.userPersistenceManager = new UserPersistenceManager(executor, db.getUserDao());
        this.statisticsDataManager = new StatisticsDataManager(executor, db.getTaskDao());
    }

    @Override
    public ITaskPersistenceManager task() {
        return taskPersistenceManager;
    }

    @Override
    public IUserPersistenceManager user() {
        return userPersistenceManager;
    }


    @Override
    public IStatisticsDataManager stats(){return statisticsDataManager;}


}
