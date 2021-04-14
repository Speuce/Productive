package com.productive6.productive.persistence.room.access;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.productive6.productive.objects.Task;

import java.util.List;

/**
 * An interface specifically handing the accessing of Tasks in the database.
 */
public interface ITaskAccess {

    /**
     * Inserts a new (no-id)
     * task into the database.
     * @param t the task to add.
     * @return the id automatically assigned to the object
     */
    @Insert
    long insertTask(Task t);

    /**
     * Gets a list of all tasks, sorted by priority
     * then date created
     * @return an {@link List} of all tasks
     */
    @Query("SELECT * FROM tasks ORDER BY priority DESC, created DESC;")
    List<Task> getAllTasks();

    /**
     * Gets a list of all complete/incomplete tasks,
     * sorted by priority then date created
     * @param complete indicates whether to look for in/complete tasks.
     * @return the list of tasks.
     */
    @Query("SELECT * FROM tasks WHERE " +
            "(:complete = 1 AND completedDay is not null) " +
            "OR (:complete = 0 AND completedDay is null) " +
            "ORDER BY priority DESC, created DESC;")
    List<Task> getAllTasks(boolean complete);

     /**
     * Updates the database information of a task
     * which has already been assigned an id
     * @param t the task to update
     */
    @Update
    void updateTask(Task t);
}
