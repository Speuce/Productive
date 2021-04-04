package com.productive6.productive.persistence.room.impl;

import android.content.Context;

import androidx.room.Room;

import com.productive6.productive.logic.adapters.ICosmeticAdapter;
import com.productive6.productive.persistence.datamanage.impl.LiveDataManager;
import com.productive6.productive.persistence.room.adapters.CosmeticConverter;
import com.productive6.productive.services.executor.IRunnableExecutor;
import com.productive6.productive.persistence.room.ProductiveDB;
import com.productive6.productive.persistence.datamanage.IDataManager;

/**
 * A production-grade {@link IDataManager} implementation that persists data using a database
 * saved locally on the device.
 */
public class PersistentAndroidDataManager extends LiveDataManager {

    private CosmeticConverter c;

    public PersistentAndroidDataManager(Context context, IRunnableExecutor executor, ICosmeticAdapter adapter) {
        super(context, executor);
        this.c = new CosmeticConverter(adapter);
    }

    @Override
    public void init() {
        db = Room.databaseBuilder(context,
                ProductiveDB.class, "productive2.db")
                .addTypeConverter(c)
                .fallbackToDestructiveMigration().build();
        super.init();
    }


}
