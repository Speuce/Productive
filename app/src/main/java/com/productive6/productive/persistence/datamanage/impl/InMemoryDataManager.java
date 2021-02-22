package com.productive6.productive.persistence.datamanage.impl;

import android.content.Context;

import androidx.room.Room;

import com.productive6.productive.persistence.ProductiveDB;
import com.productive6.productive.persistence.ITaskPersistenceManager;
import com.productive6.productive.persistence.IUserPersistenceManager;
import com.productive6.productive.persistence.datamanage.IDataManager;

/**
 * A {@link IDataManager} implementation for system and integration testing
 * using an in-memory database that will not be persisted between runs.
 */
public class InMemoryDataManager implements IDataManager {

    /**
     * A reference to the actual app object
     * provided by android
     */
    private final Context context;

    private ProductiveDB db;

    public InMemoryDataManager(Context context) {
        this.context = context;
    }

    @Override
    public void init() {
        db = Room.inMemoryDatabaseBuilder(context,
                ProductiveDB.class).allowMainThreadQueries().build();
    }

    @Override
    public ITaskPersistenceManager task() {
        return db.getTaskDao();
    }

    @Override
    public IUserPersistenceManager user() {
        return db.getUserDao();
    }

}
