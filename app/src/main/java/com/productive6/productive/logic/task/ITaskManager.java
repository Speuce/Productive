package com.productive6.productive.logic.task;

import com.productive6.productive.objects.Task;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * Interface for interacting with the logic/data layer with regards to tasks
 *
 * Implementing classes will handle verification and validation of data input
 *
 * @author Matt Kwiatkowski
 */
public interface ITaskManager {

    /**
     * Adds a new task to the users list of tasks
     * @param t the {@link Task} to add
     */
    void addTask(Task t);

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

    /**
     * @return integer representing the minimum priority value of a task

     */
     int minPriority();

    /**
     * @return integer representing the minimum priority values of a task
     */
    int minDifficulty();

}
