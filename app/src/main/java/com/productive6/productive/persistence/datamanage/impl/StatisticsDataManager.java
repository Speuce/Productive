package com.productive6.productive.persistence.datamanage.impl;

import com.productive6.productive.objects.tuples.DayIntTuple;
import com.productive6.productive.persistence.room.access.ITaskStatsticsAccess;
import com.productive6.productive.persistence.datamanage.IStatisticsDataManager;
import com.productive6.productive.services.executor.IRunnableExecutor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

/**
 * Thread-safe wrapper of {@link IStatisticsDataManager}
 */
public class StatisticsDataManager implements IStatisticsDataManager {

    private final IRunnableExecutor executor;

    private final ITaskStatsticsAccess stats;

    public StatisticsDataManager(IRunnableExecutor executor, ITaskStatsticsAccess stats) {
        this.executor = executor;
        this.stats = stats;
    }

    @Override
    public void getCompletedTasksByDay(int history, Consumer<List<DayIntTuple>> callback) {
        executor.runASync(() ->{
            List<DayIntTuple> ret = stats.getCompletedTasksByDay(history);
            executor.runSync(() -> callback.accept(ret));
        });
    }

    @Override
    public void getTotalCompletedTasks(Consumer<Integer> callback) {
        executor.runASync(() ->{
            int ret = stats.getTotalCompletedTasks();
            executor.runSync(() ->callback.accept(ret));
        });
    }

    @Override
    public void getTotalCoinsEarned(Consumer<Integer> callback) {
        executor.runASync(() ->{
            int ret = stats.getTotalCoinsEarned();
            executor.runSync(() ->callback.accept(ret));
        });
    }

    @Override
    public void getTotalXPEarned(Consumer<Integer> callback) {
        executor.runASync(() ->{
            int ret = stats.getTotalXPEarned();
            executor.runSync(() ->callback.accept(ret));
        });
    }

    @Override
    public void getFirstTaskDay(Consumer<LocalDate> callback) {
        executor.runASync(() ->{
            LocalDateTime first = stats.getFirstTaskDay();
            LocalDate ret = LocalDate.now();
            if(first != null){
                ret = first.toLocalDate();
            }
            LocalDate finalRet = ret;
            executor.runSync(() ->callback.accept(finalRet));
        });
    }
}
