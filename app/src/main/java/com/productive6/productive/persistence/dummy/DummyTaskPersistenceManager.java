package com.productive6.productive.persistence.dummy;

import com.productive6.productive.objects.Task;
import com.productive6.productive.persistence.TaskPersistenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@link com.productive6.productive.persistence.TaskPersistenceManager} for unit testing.
 * No database stuff is actually done. Everthing is just kept in internal lists.
 */
public class DummyTaskPersistenceManager implements TaskPersistenceManager {

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
    public List<Task> getAllTasks() {
        return internalList.stream().sorted().collect(Collectors.toList());
    }

    @Override
    public List<Task> getAllTasks(boolean complete) {
        return internalList.stream().filter(t -> t.isCompleted() == complete).sorted().collect(Collectors.toList());
    }

    @Override
    public void updateTask(Task t) {
        //doesn't need t really happen. Thanks pointers!
    }
}
