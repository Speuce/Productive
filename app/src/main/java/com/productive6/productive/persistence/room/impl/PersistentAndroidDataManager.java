package com.productive6.productive.persistence.room.impl;

import android.content.Context;

import androidx.room.Room;

import com.productive6.productive.persistence.datamanage.impl.LiveDataManager;
import com.productive6.productive.services.executor.IRunnableExecutor;
import com.productive6.productive.persistence.room.ProductiveDB;
import com.productive6.productive.persistence.datamanage.IDataManager;

/**
 * A production-grade {@link IDataManager} implementation that persists data using a database
 * saved locally on the device.
 */
public class PersistentAndroidDataManager extends LiveDataManager {



    public PersistentAndroidDataManager(Context context, IRunnableExecutor executor) {
        super(context, executor);
    }

    @Override
    public void init() {
        db = Room.databaseBuilder(context,
                ProductiveDB.class, "productive2.db").fallbackToDestructiveMigration().build();
        super.init();
    }


}
