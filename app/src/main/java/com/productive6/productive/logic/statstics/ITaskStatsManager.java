package com.productive6.productive.logic.statstics;

import com.productive6.productive.objects.tuples.DayIntTuple;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Interface for accessing statistics about tasks completed and whatnot
 */
public interface ITaskStatsManager {

    /**
     * Get the average number of tasks completed per day since the users first task.
     * @param callback called ONCE with the average
     */
    void getAverageTasksCompletedDaily(Consumer<Float> callback);

    /**
     * Gets the number of tasks completed all time
     * @param callback called ONCE with the total number of tasks.
     */
    void getTasksCompletedAllTime(Consumer<Integer> callback);

    /**
     * Gets a mapping day-by-day of day to # of tasks completed on said day
     * @param history how far back, from today, to go
     * @param callback called FOR EACH DAY requested
     */
    void getTasksCompletedPastDays(int history, Consumer<DayIntTuple> callback);


    /**
     * Get the date of the first task created by the user
     */
    void getFirstTaskDay(Consumer<LocalDate> callback);
}
