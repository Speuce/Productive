package com.productive6.productive.logic.rewards;

import com.productive6.productive.objects.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

public interface IStreakRewardManager extends IRewardManager{

    /**
     * Counts the current streak length in a task list
     * @param tasks the list of tasks to search for streaking tasks in
     * @param currTime The current time
     * @return The size of the current streak
     */
    int streakCount(List<Task> tasks, LocalDateTime currTime);

    /**
     * returns whether or not the user is on a streak
     * @param tasks the list of tasks to search for streaking tasks in
     * @param currTime The current time
     * @return true if on a streak
     */
    boolean onStreak(List<Task> tasks, LocalDateTime currTime);

    /**
     * a String representation of the Streak
     * @param tasks the list of tasks to search for streaking tasks in
     * @param currTime The current time
     * @return String representation stating streak length
     */
    String getStreakAsString(List<Task> tasks, LocalDateTime currTime);

}
