package com.productive6.productive.logic.task;

import com.productive6.productive.objects.Task;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * Interface for interacting with the logic layer with regards to tasks
 *
 * Implementing classes will handle verification and validation of data input
 *
 * @author Matt Kwiatkowski
 */
public interface TaskManager {

    /**
     * @return a list of the users current incomplete tasks,
     * sorted by highest priority first.
     */
    void getTasksByPriority(Consumer<List<Task>> outputparam);

    /**
     * @return a list of the users current incomplete tasks,
     * sorted by oldest creation first.
     */
    void getTasksByCreation(Consumer<List<Task>> outputparam);

    /**
     * Adds a new task to the users list of tasks
     * @param t the {@link Task} to add
     */
    void addTask(Task t);

    /**
     * @return a list of {@link Task} objects that have been marked as
     * completed
     */
    void getCompletedTasks(Consumer<List<Task>> outputparam);

    /**
     * Updates an task that is already in the system,
     * i.e changing priority or info
     * @param t the {@link Task} to change.
     */
    void updateTask(Task t);

    /**
     * Marks a task as complete
     * @param t the task that was completed.
     */
    void completeTask(Task t);
}
