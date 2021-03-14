package com.productive6.productive.persistence.access;

import androidx.room.Query;

import com.productive6.productive.objects.tuples.DayIntTuple;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface specifying use of statistics with tasks
 */
public interface ITaskStatsticsAccess extends ITaskAccess{

    /**
     * Gets a mapping of day:taskscompleted
     * @param history how far back to search
     * @return a list of {@link DayIntTuple}
     */
    @Query("SELECT STRFTIME('%s',dat) as day, number FROM " +
            "(SELECT JULIANDAY(DATE(ROUND(completedDay/1000) , 'unixepoch')) as dat, COUNT(id) as number" +
            " FROM tasks " +
            "WHERE dat>=(julianday(date('now'))-:history)" +
            "AND dat NOT NULL " +
            "GROUP BY dat " +
            "ORDER BY dat);")
    List<DayIntTuple> getCompletedTasksByDay(int history);

    /**
     * Get the total # of tasks completed by the user
     */
    @Query("SELECT COUNT(id) FROM tasks WHERE completedDay NOT NULL ")
    int getTotalCompletedTasks();

    /**
     * Get the total # of coins earned by the user
     */
    @Query("SELECT SUM(coins) FROM tasks WHERE completedDay NOT NULL")
    int getTotalCoinsEarned();

    /**
     * Get the total xp earned by the user
     */
    @Query("SELECT SUM(xp) FROM tasks WHERE completedDay NOT NULL")
    int getTotalXPEarned();

    /**
     * Get the first day that the user created a task.
     */
    @Query("SELECT MIN(created) FROM tasks")
    LocalDateTime getFirstTaskDay();

}
