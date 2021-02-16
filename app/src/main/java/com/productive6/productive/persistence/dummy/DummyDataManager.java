package com.productive6.productive.persistence.dummy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Room;

import com.productive6.productive.objects.Task;
import com.productive6.productive.persistence.DataManager;
import com.productive6.productive.persistence.TaskPersistenceManager;


/**
 * A data manager for unit tests.
 * No database stuff is actually done. Everthing is just kept in internal lists.
 */
public class DummyDataManager implements DataManager {

    /**
     * Associated {@link DummyTaskPersistenceManager}
     */
    private final TaskPersistenceManager taskPersistenceManager;

    public DummyDataManager() {
        this.taskPersistenceManager = new DummyTaskPersistenceManager();
    }

    @Override
    public void init() {
        //empty
    }

    @Override
    public TaskPersistenceManager task() {
        return taskPersistenceManager;
    }


}
