package com.productive6.productive.persistence.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.productive6.productive.objects.Task;
import com.productive6.productive.persistence.TaskPersistenceManager;

import java.util.List;

/**
 * Android-specific implementation of a data access object for Tasks
 */
@Dao
public interface TaskDao extends TaskPersistenceManager {

    /**
     * Inserts a new (no-id)
     * task into the database.
     * An id is automatically added to the object.
     * @param t the task to add.
     */
    @Insert
    void insertTask(Task t);

    /**
     * Gets a list of all tasks
     * @return an {@link List} of all tasks
     */
    @Query("SELECT * FROM tasks;")
    List<Task> getAllTasks();

    /**
     * Updates the database information of a task
     * which has already been assigned an id
     * @param t the task to update
     */
    @Update
    void updateTask(Task t);
}
