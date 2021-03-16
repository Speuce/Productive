package com.productive6.productive.logic.statstics;

import com.productive6.productive.objects.tuples.DayIntTuple;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Interface for accessing statistics about coins earned and whatnot
 */
public interface ICoinsStatsManager {

    /**
     * Gets the number of coins earned all time
     * @param callback called ONCE with the total number of tasks.
     */
    void getCoinsEarnedAllTime(Consumer<Integer> callback);
}
