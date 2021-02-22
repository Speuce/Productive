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
     * @return an instance of a {@link ITaskAccess}
     * to perform database inserts/updates of tasks.
     */
    ITaskAccess task();

    /**
     * @return an instance of a {@link IUserAccess}
     * to perform CRUD operations with users.
     */
    IUserAccess user();


}
