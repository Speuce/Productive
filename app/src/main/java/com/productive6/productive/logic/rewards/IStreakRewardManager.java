package com.productive6.productive.logic.rewards;

import com.productive6.productive.objects.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

public interface IStreakRewardManager extends IRewardManager{


    /**
     * Returns the time period (hours) over which a streak runs
     * @return
     */
    int getStreakConstant();

    /**
     * Counts the current streak length in a task list
     * @param tasks the list of tasks to search for streaking tasks in
     * @param currTime The current time
     * @return The size of the current streak
     */
    int inStreakTime(List<Task> tasks, LocalDateTime currTime);

}
