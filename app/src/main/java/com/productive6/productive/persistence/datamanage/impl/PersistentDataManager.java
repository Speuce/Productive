package com.productive6.productive.persistence.datamanage.impl;

import android.content.Context;

import androidx.room.Room;

import com.productive6.productive.persistence.ProductiveDB;
import com.productive6.productive.persistence.ITaskPersistenceManager;
import com.productive6.productive.persistence.IUserPersistenceManager;
import com.productive6.productive.persistence.datamanage.IDataManager;

import javax.inject.Inject;

/**
 * A production-grade {@link IDataManager} implementation that persists data using a database
 * saved locally on the device.
 */
public class PersistentDataManager implements IDataManager {

    /**
     * A reference to the actual app object
     * provided by android
     */
    private final Context context;

    private ProductiveDB db;

    @Inject
    public PersistentDataManager(Context context) {
        this.context = context;
    }

    @Override
    public void init() {
        db = Room.databaseBuilder(context,
                ProductiveDB.class, "productive.db").build();
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