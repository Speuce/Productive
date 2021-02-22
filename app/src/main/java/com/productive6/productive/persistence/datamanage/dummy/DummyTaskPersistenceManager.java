package com.productive6.productive.persistence.datamanage.dummy;

import com.productive6.productive.objects.Task;
import com.productive6.productive.persistence.access.ITaskAccess;
import com.productive6.productive.persistence.datamanage.ITaskPersistenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A {@link ITaskAccess} for unit testing.
 * No database stuff is actually done. Everything is just kept in internal lists.
 */
public class DummyTaskPersistenceManager implements ITaskPersistenceManager {



    /**
     * Internal list of tasks.
     */
    private final ArrayList<Task> internalList;

    public DummyTaskPersistenceManager() {
        this.internalList = new ArrayList<>();
    }

    @Override
    public void insertTask(Task t) {
        t.setId(internalList.size());
        internalList.add(t);
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
    public void updateTask(Task t) {
        //doesn't need t really happen. Thanks pointers!
    }
}
