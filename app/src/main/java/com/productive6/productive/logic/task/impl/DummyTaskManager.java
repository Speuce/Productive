package com.productive6.productive.logic.task.impl;

import com.productive6.productive.logic.task.TaskManager;
import com.productive6.productive.objects.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A dummy/test implementation of the {@link com.productive6.productive.logic.task.TaskManager}
 * interface for unit testing purposes.
 * No Database access here.
 * @author Matt Kwiatkowski
 */
public class DummyTaskManager implements TaskManager {

    /**
     * Internal list of tasks. Since this is a unit test, this shant be persisted
     */
    private List<Task> tasks;

    public DummyTaskManager() {
        this.tasks = new ArrayList<>();
    }

    @Override
    public void getTasksByPriority(Consumer<List<Task>> outputparam) {
        outputparam.accept(tasks.stream().filter(t -> !t.isCompleted()).sorted().collect(Collectors.toList()));
    }

    @Override
    public void getTasksByCreation(Consumer<List<Task>> outputparam) {
        outputparam.accept(tasks.stream().filter(t -> !t.isCompleted()).sorted((o1, o2) ->
                (int) (o2.getCreatedTime()-o1.getCreatedTime())).collect(Collectors.toList()));
    }

    @Override
    public void addTask(Task t) {
        tasks.add(t);
    }

    @Override
    public void getCompletedTasks(Consumer<List<Task>> outputparam) {
        outputparam.accept(tasks.stream().filter(Task::isCompleted).sorted((o1, o2) ->
                (int) (o2.getCreatedTime()-o1.getCreatedTime())).collect(Collectors.toList()));
    }

    @Override
    public void updateTask(Task t) {
        //updates are taken care of since these objects don't need to be persisted.
    }
}
