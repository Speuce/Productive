package com.productive6.productive.logic.statstics.impl;

import com.productive6.productive.logic.statstics.ICoinsStatsManager;
import com.productive6.productive.logic.statstics.ITaskStatsManager;
import com.productive6.productive.logic.statstics.IXPStatsManager;
import com.productive6.productive.objects.tuples.DayIntTuple;
import com.productive6.productive.persistence.datamanage.IDataManager;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            //we need to give a value for ALL the days, even ones missed by the data layer
            LocalDate start = LocalDate.now();
            List<LocalDate> dates = Stream.iterate(start, date -> date.minusDays(1))
                    .limit(history+1)
                    .collect(Collectors.toList());
            dates.forEach(day ->{
                boolean found = false;
                for (Iterator<DayIntTuple> iterator = list.iterator(); iterator.hasNext(); ) {
                    DayIntTuple tuple = iterator.next();

                    if (tuple.getDate().equals(day)) {
                        found = true;
                        callback.accept(new DayIntTuple(day, tuple.getNumber()));
                        iterator.remove();
                        break;
                    }
                }
                if(!found){
                    callback.accept(new DayIntTuple(day, 0));
                }
            });
        });
    }

    @Override
    public void getFirstTaskDay(Consumer<LocalDate> callback) {
        dataManager.stats().getFirstTaskDay(callback);
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
