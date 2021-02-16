package com.productive6.productive.persistence;

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


}
