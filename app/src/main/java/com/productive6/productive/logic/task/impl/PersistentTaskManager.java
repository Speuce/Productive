package com.productive6.productive.logic.task.impl;

import com.productive6.productive.executor.RunnableExecutor;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.exceptions.PersistentIDAssignmentException;
import com.productive6.productive.logic.exceptions.TaskFormatException;
import com.productive6.productive.logic.task.TaskManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;
import com.productive6.productive.objects.events.task.TaskCreateEvent;
import com.productive6.productive.objects.events.task.TaskUpdateEvent;
import com.productive6.productive.persistence.datamanage.DataManager;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * A production-grade and persistent implementation of the
 * {@link TaskManager} interface
 */
public class PersistentTaskManager implements TaskManager {


    private DataManager data;

    private RunnableExecutor executor;


    public PersistentTaskManager(DataManager data, RunnableExecutor e) {
        this.data = data;
        this.executor = e;
    }

    @Override
    public void getTasksByPriority(Consumer<Collection<Task>> outputparam) {
        executor.runASync(() -> {
            Collection<Task> ret = data.task().getAllTasks(false);
            executor.runSync(() -> outputparam.accept(ret));
        });
    }

    @Override
    public void getTasksByCreation(Consumer<Collection<Task>> outputparam) {
        executor.runASync(() -> {
            List<Task> ret = data.task().getAllTasks(false);
            ret.sort((a, b) -> (int) (((Task) a).getCreatedTime() - ((Task) b).getCreatedTime()));
            executor.runSync(() -> outputparam.accept(ret));
        });
    }

    /**
     * Verifies a passed task, and if successful saves it to the database
     * and assigns it an id.
     * @param t the {@link Task} to add
     */
    @Override
    public void addTask(Task t) {
        if(t.getId() > 0){
            throw new PersistentIDAssignmentException();
        }else if(t.isCompleted()){
              throw new TaskFormatException("A new task was passed with completed=true!");
        }else if(t.getPriority() < 0){
                throw new TaskFormatException("A priority of < 0 is not supported!");
        }

        if(t.getCreatedTime() == 0){
            t.setCreatedTime(System.currentTimeMillis());
        }

        executor.runASync(() -> data.task().insertTask(t));

        EventDispatch.dispatchEvent(new TaskCreateEvent(t));

    }

    @Override
    public void getCompletedTasks(Consumer<Collection<Task>> outputparam) {
        executor.runASync(() ->{
            Collection<Task> ret = data.task().getAllTasks(true);
            executor.runSync(() -> outputparam.accept(ret));
        });
    }

    @Override
    public void updateTask(Task t) {
        if(t.getId() == 0){
            throw new PersistentIDAssignmentException();
        }
        executor.runASync(() -> data.task().updateTask(t));
        EventDispatch.dispatchEvent(new TaskUpdateEvent(t));
    }

    @Override
    public void completeTask(Task t) {
        if(t.isCompleted()){
            throw new TaskFormatException("Task has already been completed!");
        }
        t.setCompleted(true);
        updateTask(t);
        EventDispatch.dispatchEvent(new TaskCompleteEvent(t));
    }
}
