package com.productive6.productive.persistence.datamanage;

import android.content.Context;

import androidx.room.Room;

import com.productive6.productive.persistence.ProductiveDB;
import com.productive6.productive.persistence.TaskPersistenceManager;
import com.productive6.productive.persistence.UserPersistenceManager;
import com.productive6.productive.persistence.datamanage.DataManager;

import javax.inject.Inject;

/**
 * A production-grade {@link DataManager} implementation that persists data using a database
 * saved locally on the device.
 */
public class PersistentDataManager implements DataManager{

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
    public TaskPersistenceManager task() {
        return db.getTaskDao();
    }

    @Override
    public UserPersistenceManager user() {
        return db.getUserDao();
    }


}
