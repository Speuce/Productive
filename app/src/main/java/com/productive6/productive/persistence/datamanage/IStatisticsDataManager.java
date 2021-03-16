package com.productive6.productive.persistence.datamanage;

import androidx.room.Query;

import com.productive6.productive.objects.tuples.DayIntTuple;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

/**
 * An interface for interacting with the data layer with regards to statistics
 * Implementations should be thread safe, and callbacks will be called on the main thread.
 */
public interface IStatisticsDataManager {

    /**
     * Gets a mapping of day:taskscompleted
     * @param history how far back to search
     * @return a list of {@link DayIntTuple}
     */
    void getCompletedTasksByDay(int history, Consumer<List<DayIntTuple>> callback);

    /**
     * Get the total # of tasks completed by the user
     */
    void getTotalCompletedTasks(Consumer<Integer> callback);


    /**
     * Get the total # of coins earned by the user
     */
    void getTotalCoinsEarned(Consumer<Integer> callback);

    /**
     * Get the total xp earned by the user
     */
    void getTotalXPEarned(Consumer<Integer> callback);

    /**
     * Get the first day that the user created a task.
     */
    void getFirstTaskDay(Consumer<LocalDate> callback);

}
