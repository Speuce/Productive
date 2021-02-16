package com.productive6.productive.persistence;

import android.content.Context;

import androidx.room.Room;

import com.productive6.productive.objects.Task;

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


}
