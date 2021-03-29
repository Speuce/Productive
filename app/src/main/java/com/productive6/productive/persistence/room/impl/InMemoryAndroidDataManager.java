package com.productive6.productive.persistence.room.impl;

import android.content.Context;

import androidx.room.Room;

import com.productive6.productive.persistence.datamanage.impl.LiveDataManager;
import com.productive6.productive.persistence.room.ProductiveDB;
import com.productive6.productive.persistence.datamanage.IDataManager;
import com.productive6.productive.services.executor.IRunnableExecutor;

/**
 * A production-grade {@link IDataManager} implementation that persists data using a database
 * saved locally on the device memory. The data should not persist between re-runs of the app.
 */
public class InMemoryAndroidDataManager extends LiveDataManager {

    public InMemoryAndroidDataManager(Context context, IRunnableExecutor executor) {
        super(context, executor);
    }

    @Override
    public void init() {
        db = Room.inMemoryDatabaseBuilder(context,
                ProductiveDB.class).allowMainThreadQueries().build();
        super.init();
    }



}
