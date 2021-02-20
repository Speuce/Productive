package com.productive6.productive.persistence.datamanage;

import com.productive6.productive.persistence.TaskPersistenceManager;
import com.productive6.productive.persistence.UserPersistenceManager;

/**
 * Interface for classes managing persistent data
 * @author matt
 */
public interface DataManager {

    /**
     * A chance for implementing classes to perform any
     * initialization procedures.
     */
    void init();

    /**
     * @return an instance of a {@link TaskPersistenceManager}
     * to perform database inserts/updates of tasks.
     */
    TaskPersistenceManager task();

    /**
     * @return an instance of a {@link UserPersistenceManager}
     * to perform CRUD operations with users.
     */
    UserPersistenceManager user();


}
