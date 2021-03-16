package com.productive6.productive.logic.task;

import com.productive6.productive.objects.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

/**
 * Interface for sorting tasks by various methods
 */
public interface ITaskSorter {

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
     * @return a list of the  user's incomplete tasks, with the closest-due first.
     */
    void getTasksByDueDate(Consumer<List<Task>> outputparam);

    /**
     * Get all tasks with a due date on the specific given day
     * @param d the {@link java.util.Date} to search on.
     */
    void getTasksOnDate(LocalDate d, Consumer<List<Task>> outputparam);

    /**
     * Get the days of a month that have a task associated with it
     * @param startofMonth the starting day of said month
     * @param outputparam called ONCE for EACH day with a task
     */
    void getDaysWithTaskInMonth(LocalDate startofMonth, Consumer<LocalDate> outputparam);

    /**
     * @return a list of {@link Task} objects that have been marked as
     * completed
     */
    void getCompletedTasks(Consumer<List<Task>> outputparam);
}
