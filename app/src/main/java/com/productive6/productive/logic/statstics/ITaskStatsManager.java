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
     * Gets a mapping of dayOfWeek:tasks completed on said day, going ALL the way back
     * @param callback called ONCE, providing a mapping of DayOFweek:integer
     */
    void getTasksCompletedByDayOfWeek(Consumer<Map<DayOfWeek, Integer>> callback);

    /**
     * Gets a mapping of hour of day (0-23):total tasks completed, going ALL the way back
     * @param callback called ONCE providing a mapping of HourOfDay (byte):Integer
     */
    void getTasksCompletedByHourOfDay(Consumer<Map<Byte, Integer>> callback);

    /**
     * Get the date of the first task created by the user
     */
    void getFirstTaskDay(Consumer<LocalDate> callback);
}
