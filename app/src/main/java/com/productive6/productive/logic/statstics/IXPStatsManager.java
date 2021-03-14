package com.productive6.productive.logic.statstics;

import com.productive6.productive.objects.tuples.DayIntTuple;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Consumer;


public interface IXPStatsManager {

    /**
     * Gets the number of XP earned all time
     * @param callback called ONCE with the total number of tasks.
     */
    void getXPEarnedAllTime(Consumer<Integer> callback);


}
