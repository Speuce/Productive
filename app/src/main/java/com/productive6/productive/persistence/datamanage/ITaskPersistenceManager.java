package com.productive6.productive.persistence.datamanage;

import com.productive6.productive.objects.Task;

import java.util.List;
import java.util.function.Consumer;

/**
 * An interface for interacting with the data layer with regards to tasks
 * Implementations should be thread safe, and callbacks will be called on the main thread.
 */
public interface ITaskPersistenceManager {

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
     * @param callback an {@link List} of all tasks
     */
    void getAllTasks(Consumer<List<Task>> callback);

    /**
     * Gets a list of all complete/incomplete tasks,
     * sorted by priority then date created
     * @param complete indicates whether to look for in/complete tasks.
     * @param callback the list of tasks.
     */
    void getAllTasks(boolean complete, Consumer<List<Task>> callback);

    /**
     * Updates the database information of a task
     * which has already been assigned an id
     * @param t the task to update
     */
    void updateTask(Task t);
}
