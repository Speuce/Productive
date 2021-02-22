package com.productive6.productive.persistence.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.productive6.productive.objects.Task;
import com.productive6.productive.persistence.access.ITaskAccess;

import java.util.List;

/**
 * Android-specific implementation of a data access object for Tasks
 */
@Dao
public interface TaskDao extends ITaskAccess {

    /**
     * Inserts a new (no-id)
     * task into the database.
     * An id is automatically added to the object.
     * @param t the task to add.
     */
    @Insert
    void insertTask(Task t);

    /**
     * Gets a list of all tasks, sorted by priority
     * then date created
     * @return an {@link List} of all tasks
     */
    @Query("SELECT * FROM tasks ORDER BY priority, created;")
    List<Task> getAllTasks();

    /**
     * Gets a list of all complete/incomplete tasks,
     * sorted by priorit then date created
     * @param complete indicates whether to look for in/complete tasks.
     * @return the list of tasks.
     */
    @Query("SELECT * FROM tasks WHERE completed = :complete ORDER BY priority, created;")
    List<Task> getAllTasks(boolean complete);

    /**
     * Updates the database information of a task
     * which has already been assigned an id
     * @param t the task to update
     */
    @Update
    void updateTask(Task t);
}
