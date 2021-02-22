package com.productive6.productive.persistence.dummy;

import com.productive6.productive.persistence.UserPersistenceManager;
import com.productive6.productive.persistence.datamanage.DataManager;
import com.productive6.productive.persistence.TaskPersistenceManager;


/**
 * A data manager for unit tests.
 * No database stuff is actually done. Everthing is just kept in internal lists.
 */
public class DummyDataManager implements DataManager {

    /**
     * Associated {@link DummyTaskPersistenceManager}
     */
    private final TaskPersistenceManager taskPersistenceManager;

    private final UserPersistenceManager userPersistenceManager;

    public DummyDataManager() {
        this.taskPersistenceManager = new DummyTaskPersistenceManager();
        this.userPersistenceManager = new DummyUserPersistenceManager();
    }

    @Override
    public void init() {
        //empty
    }

    @Override
    public TaskPersistenceManager task() {
        return taskPersistenceManager;
    }

    @Override
    public UserPersistenceManager user() {
        return userPersistenceManager;
    }


}
