package com.productive6.productive.logic.task.impl;

import com.productive6.productive.logic.exceptions.PersistentIDAssignmentException;
import com.productive6.productive.logic.exceptions.TaskFormatException;
import com.productive6.productive.logic.task.TaskManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.persistence.DataManager;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import javax.inject.Inject;

/**
 * A production-grade and persistent implementation of the
 * {@link TaskManager} interface
 */
public class PersistentTaskManager implements TaskManager {


    private DataManager data;

    private ExecutorService e;


    public PersistentTaskManager(DataManager data, ExecutorService e) {
        this.data = data;
        this.e = e;
    }

    @Override
    public void getTasksByPriority(Consumer<Collection<Task>> outputparam) {

    }

    @Override
    public void getTasksByCreation(Consumer<Collection<Task>> outputparam) {

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
        data.task().insertTask(t);


    }

    @Override
    public void getCompletedTasks(Consumer<Collection<Task>> outputparam) {

    }

    @Override
    public void updateTask(Task t) {
        if(t.getId() == 0){
            throw new PersistentIDAssignmentException();
        }
        data.task().updateTask(t);
    }
}
