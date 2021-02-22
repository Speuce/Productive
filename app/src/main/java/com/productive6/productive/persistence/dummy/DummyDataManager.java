package com.productive6.productive.persistence.dummy;

import com.productive6.productive.persistence.IUserPersistenceManager;
import com.productive6.productive.persistence.datamanage.IDataManager;
import com.productive6.productive.persistence.ITaskPersistenceManager;


/**
 * A data manager for unit tests.
 * No database stuff is actually done. Everthing is just kept in internal lists.
 */
public class DummyDataManager implements IDataManager {

    /**
     * Associated {@link DummyTaskPersistenceManager}
     */
    private final ITaskPersistenceManager taskPersistenceManager;

    private final IUserPersistenceManager userPersistenceManager;

    public DummyDataManager() {
        this.taskPersistenceManager = new DummyTaskPersistenceManager();
        this.userPersistenceManager = new DummyUserPersistenceManager();
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


}
