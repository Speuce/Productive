package com.productive6.productive.persistence.datamanage.dummy;

import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.tuples.DayIntTuple;
import com.productive6.productive.persistence.room.access.ITaskAccess;
import com.productive6.productive.persistence.datamanage.IStatisticsDataManager;
import com.productive6.productive.persistence.datamanage.ITaskPersistenceManager;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A {@link ITaskAccess} for unit testing.
 * No database stuff is actually done. Everything is just kept in internal lists.
 *
 * Used streams for most things. Java Streams behave alot like SQL queries, so they make a good
 * candidate for dummy-ing
 */
public class DummyTaskPersistenceManager implements ITaskPersistenceManager, IStatisticsDataManager {


    /**
     * Internal list of tasks.
     */
    private final ArrayList<Task> internalList;

    public DummyTaskPersistenceManager() {
        this.internalList = new ArrayList<>();
    }

    @Override
    public void insertTask(Task t, Runnable r) {

        internalList.add(t);
        t.setId(internalList.size());
        r.run();
    }

    @Override
    public void getAllTasks(Consumer<List<Task>> callback) {
        callback.accept(internalList.stream().sorted().collect(Collectors.toList()));
    }

    @Override
    public void getAllTasks(boolean complete, Consumer<List<Task>> callback) {
        callback.accept(internalList.stream().filter(t -> t.isCompleted() == complete).sorted().collect(Collectors.toList()));
    }

    @Override
    public void updateTask(Task t, Runnable after) {
        //doesn't need t really happen. Thanks pointers!
        after.run();
    }

    @Override
    public void getCompletedTasksByDay(int history, Consumer<List<DayIntTuple>> callback) {
        LocalDate before = LocalDate.now().minusDays(history);
        callback.accept(internalList.stream().filter(Task::isCompleted).filter(task -> task.getCompleted().isAfter(before.atStartOfDay())).collect(
                Collectors.groupingBy(task -> task.getCompleted().toLocalDate(), Collectors.counting()))
                .entrySet().stream()
                .map(e ->new DayIntTuple(e.getKey(), e.getValue().intValue()))
                .collect(Collectors.toList()));
    }

    @Override
    public void getTotalCompletedTasks(Consumer<Integer> callback) {
        callback.accept((int) internalList.stream().filter(Task::isCompleted).count());
    }

    @Override
    public void getTotalCoinsEarned(Consumer<Integer> callback) {
        callback.accept(internalList.stream().filter(Task::isCompleted).mapToInt(Task::getCoinsEarned).sum());
    }

    @Override
    public void getTotalXPEarned(Consumer<Integer> callback) {
        callback.accept(internalList.stream().filter(Task::isCompleted).mapToInt(Task::getXpEarned).sum());
    }

    @Override
    public void getFirstTaskDay(Consumer<LocalDate> callback) {
        OptionalLong opt = internalList.stream().mapToLong(task -> task.getCreatedTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).min();
        if(opt.isPresent()){
            callback.accept(Instant.ofEpochMilli(opt.getAsLong()).atZone(ZoneId.systemDefault()).toLocalDate());
        }else{
            callback.accept(LocalDate.now());
        }
    }
}
