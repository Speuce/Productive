package com.productive6.productive.logic.statstics.impl;

import com.productive6.productive.logic.statstics.ICoinsStatsManager;
import com.productive6.productive.logic.statstics.ITaskStatsManager;
import com.productive6.productive.logic.statstics.IXPStatsManager;
import com.productive6.productive.objects.tuples.DayIntTuple;
import com.productive6.productive.persistence.datamanage.IDataManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Consumer;

import static java.time.temporal.ChronoUnit.DAYS;

public class StatsManager implements ITaskStatsManager, ICoinsStatsManager, IXPStatsManager {

    private final IDataManager dataManager;

    public StatsManager(IDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getAverageTasksCompletedDaily(Consumer<Float> callback) {
        dataManager.stats().getTotalCompletedTasks(tasksCompleted ->{
            dataManager.stats().getFirstTaskDay(firstDay ->{
                float daysBetween = (float) DAYS.between(firstDay,LocalDate.now()) + 1;
                callback.accept(tasksCompleted/daysBetween);
            });
        });
    }

    @Override
    public void getTasksCompletedAllTime(Consumer<Integer> callback) {
        dataManager.stats().getTotalCompletedTasks(callback);
    }

    @Override
    public void getTasksCompletedPastDays(int history, Consumer<DayIntTuple> callback) {
        dataManager.stats().getCompletedTasksByDay(history, (list) ->{
            list.forEach(callback);
        });
    }

    @Override
    public void getTasksCompletedByDayOfWeek(Consumer<Map<DayOfWeek, Integer>> callback) {

    }

    @Override
    public void getTasksCompletedByHourOfDay(Consumer<Map<Byte, Integer>> callback) {

    }

    @Override
    public void getFirstTaskDay(Consumer<LocalDate> callback) {
        dataManager.stats().getFirstTaskDay(callback);
    }


    @Override
    public void getCoinsEarnedPastDays(int history, Consumer<DayIntTuple> callback) {

    }

    @Override
    public void getXPEarnedPastDays(int history, Consumer<DayIntTuple> callback) {

    }

    @Override
    public void getXPEarnedAllTime(Consumer<Integer> callback) {
        dataManager.stats().getTotalXPEarned(callback);
    }

    @Override
    public void getCoinsEarnedAllTime(Consumer<Integer> callback) {
        dataManager.stats().getTotalCoinsEarned(callback);

    }
}
