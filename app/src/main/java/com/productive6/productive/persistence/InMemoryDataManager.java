package com.productive6.productive.persistence;

import android.content.Context;

import androidx.room.Room;

import com.productive6.productive.objects.Task;

/**
 * A {@link DataManager} implementation for system and integration testing
 * using an in-memory database that will not be persisted between runs.
 */
public class InMemoryDataManager implements DataManager{

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
    public TaskPersistenceManager task() {
        return db.getTaskDao();
    }

}
