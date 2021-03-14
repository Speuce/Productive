package com.productive6.productive.logic.statstics;

import com.productive6.productive.objects.tuples.DayIntTuple;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Consumer;


public interface IXPStatsManager {


    /**
     * Gets a mapping day-by-day of day to xp earned on said day
     * @param history how far back, from today, to go
     * @param callback called FOR EACH DAY requested
     */
    void getXPEarnedPastDays(int history, Consumer<DayIntTuple> callback);


    /**
     * Gets the number of XP earned all time
     * @param callback called ONCE with the total number of tasks.
     */
    void getXPEarnedAllTime(Consumer<Integer> callback);


}
