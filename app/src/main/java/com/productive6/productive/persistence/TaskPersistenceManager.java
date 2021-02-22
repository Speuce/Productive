package com.productive6.productive.persistence;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.productive6.productive.objects.Task;

import java.util.List;

/**
 * An interface specifically handing the persistence of tasks
 */
public interface TaskPersistenceManager {

    /**
     * Inserts a new (no-id)
     * task into the database.
     * An id is automatically added to the object.
     * @param t the task to add.
     */
    void insertTask(Task t);

    /**
     * Gets a list of all tasks, sorted by
     * priority then date created
     * @return an {@link List} of all tasks
     */
    List<Task> getAllTasks();

    /**
     * Gets a list of all complete/incomplete tasks,
     * sorted by priorit then date created
     * @param complete indicates whether to look for in/complete tasks.
     * @return the list of tasks.
     */
    List<Task> getAllTasks(boolean complete);

    /**
     * Updates the database information of a task
     * which has already been assigned an id
     * @param t the task to update
     */
    void updateTask(Task t);
}
