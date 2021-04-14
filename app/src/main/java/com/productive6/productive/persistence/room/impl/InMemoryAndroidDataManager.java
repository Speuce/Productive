package com.productive6.productive.persistence.room.impl;

import android.content.Context;

import androidx.room.Room;

import com.productive6.productive.logic.adapters.ICosmeticAdapter;
import com.productive6.productive.persistence.datamanage.impl.LiveDataManager;
import com.productive6.productive.persistence.room.ProductiveDB;
import com.productive6.productive.persistence.datamanage.IDataManager;
import com.productive6.productive.persistence.room.adapters.CosmeticConverter;
import com.productive6.productive.services.executor.IRunnableExecutor;

/**
 * A production-grade {@link IDataManager} implementation that persists data using a database
 * saved locally on the device memory. The data should not persist between re-runs of the app.
 */
public class InMemoryAndroidDataManager extends LiveDataManager {

    private CosmeticConverter c;

    public InMemoryAndroidDataManager(Context context, IRunnableExecutor executor, ICosmeticAdapter adapter) {
        super(context, executor);
        this.c = new CosmeticConverter(adapter);
    }


    @Override
    public void init() {
        db = Room.inMemoryDatabaseBuilder(context,
                ProductiveDB.class)
                .addTypeConverter(c).allowMainThreadQueries().build();
        super.init();
    }



}
