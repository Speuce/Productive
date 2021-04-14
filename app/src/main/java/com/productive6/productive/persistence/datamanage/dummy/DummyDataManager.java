package com.productive6.productive.persistence.datamanage.dummy;

import com.productive6.productive.persistence.datamanage.IDataManager;
import com.productive6.productive.persistence.datamanage.IStatisticsDataManager;
import com.productive6.productive.persistence.datamanage.ITaskPersistenceManager;
import com.productive6.productive.persistence.datamanage.IUserPersistenceManager;


/**
 * A data manager for unit tests.
 * No database stuff is actually done. Everything is just kept in internal lists.
 */
public class DummyDataManager implements IDataManager {

    /**
     * Associated {@link DummyTaskPersistenceManager}
     */
    private final ITaskPersistenceManager taskPersistenceManager;

    private final IUserPersistenceManager userPersistenceManager;

    private final IStatisticsDataManager statisticsDataManager;

    public DummyDataManager() {
        this.taskPersistenceManager = new DummyTaskPersistenceManager();
        this.userPersistenceManager = new DummyUserPersistenceManager();
        this.statisticsDataManager = (IStatisticsDataManager)taskPersistenceManager;
    }

    @Override
    public void init() {
        //empty
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
    public IStatisticsDataManager stats() {
        return statisticsDataManager;
    }


}
