package com.productive6.productive.persistence.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.User;
import com.productive6.productive.persistence.room.adapters.Converters;
import com.productive6.productive.persistence.room.adapters.CosmeticConverter;
import com.productive6.productive.persistence.room.daos.TaskDao;
import com.productive6.productive.persistence.room.daos.UserDao;

/**
 * Default database structure code required by Room.
 * Implementation is auto-generated by the library.
 */
@Database(entities = {Task.class, User.class}, version = 5, exportSchema = false)
@TypeConverters({Converters.class, CosmeticConverter.class})
public abstract class ProductiveDB extends RoomDatabase {

    public abstract TaskDao getTaskDao();

    public abstract UserDao getUserDao();
}
