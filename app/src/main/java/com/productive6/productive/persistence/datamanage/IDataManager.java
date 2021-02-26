package com.productive6.productive.persistence.datamanage;

import com.productive6.productive.persistence.access.ITaskAccess;
import com.productive6.productive.persistence.access.IUserAccess;

/**
 * Interface for classes managing persistent data
 * @author matt
 */
public interface IDataManager {

    /**
     * A chance for implementing classes to perform any
     * initialization procedures.
     */
    void init();

    /**
     * @return an instance of a {@link ITaskPersistenceManager}
     * to perform database operations with tasks.
     */
    ITaskPersistenceManager task();

    /**
     * @return an instance of a {@link IUserPersistenceManager}
     * to perform database operations with users.
     */
    IUserPersistenceManager user();


}
