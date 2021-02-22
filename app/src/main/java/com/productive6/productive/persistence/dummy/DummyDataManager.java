package com.productive6.productive.persistence.dummy;

import com.productive6.productive.persistence.access.IUserAccess;
import com.productive6.productive.persistence.datamanage.IDataManager;
import com.productive6.productive.persistence.access.ITaskAccess;


/**
 * A data manager for unit tests.
 * No database stuff is actually done. Everthing is just kept in internal lists.
 */
public class DummyDataManager implements IDataManager {

    /**
     * Associated {@link DummyTaskPersistenceManager}
     */
    private final ITaskAccess taskPersistenceManager;

    private final IUserAccess userPersistenceManager;

    public DummyDataManager() {
        this.taskPersistenceManager = new DummyTaskPersistenceManager();
        this.userPersistenceManager = new DummyUserPersistenceManager();
    }

    @Override
    public void init() {
        //empty
    }

    @Override
    public ITaskAccess task() {
        return taskPersistenceManager;
    }

    @Override
    public IUserAccess user() {
        return userPersistenceManager;
    }


}
