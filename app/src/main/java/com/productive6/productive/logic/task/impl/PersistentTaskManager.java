package com.productive6.productive.logic.task.impl;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.exceptions.PersistentIDAssignmentException;
import com.productive6.productive.logic.exceptions.ObjectFormatException;
import com.productive6.productive.logic.task.TaskManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;
import com.productive6.productive.objects.events.task.TaskCreateEvent;
import com.productive6.productive.objects.events.task.TaskUpdateEvent;
import com.productive6.productive.persistence.datamanage.IDataManager;

import java.util.List;
import java.util.function.Consumer;

/**
 * A production-grade and persistent implementation of the
 * {@link TaskManager} interface
 */
public class PersistentTaskManager implements TaskManager {


    private IDataManager data;



    public PersistentTaskManager(IDataManager data) {
        this.data = data;
    }

    @Override
    public void getTasksByPriority(Consumer<List<Task>> outputparam) {
        data.task().getAllTasks(false, outputparam);
    }

    @Override
    public void getTasksByCreation(Consumer<List<Task>> outputparam) {
        data.task().getAllTasks(false, ret ->{
            ret.sort((a, b) -> (int) (((Task) a).getCreatedTime() - ((Task) b).getCreatedTime()));
            outputparam.accept(ret);
        });
    }

    /**
     * Ensures that all the fields of the given task are valid.
     * Throws exceptions otherwise.
     * @param t the task to validate.
     */
    private void validateTask(Task t){
        if(t.getPriority() < 0){
            throw new ObjectFormatException("A priority of < 0 is not supported!");
        }else {}
//            if(t.getDueTime() != 0  && t.getDueTime() < System.currentTimeMillis()){
//            throw new ObjectFormatException("A task cannot have a due time before now");
//        }
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
              throw new ObjectFormatException("A new task was passed with completed=true!");
        }else if(t.getPriority() < 0){
                throw new ObjectFormatException("A priority of < 0 is not supported!");
        }
        validateTask(t);
        if(t.getCreatedTime() == 0){
            t.setCreatedTime(System.currentTimeMillis());
        }
        data.task().insertTask(t);
        EventDispatch.dispatchEvent(new TaskCreateEvent(t));

    }

    @Override
    public void getCompletedTasks(Consumer<List<Task>> outputparam) {
        data.task().getAllTasks(true, outputparam);
    }

    @Override
    public void updateTask(Task t) {
        if(t.getId() == 0){
            throw new PersistentIDAssignmentException();
        }
        validateTask(t);
        data.task().updateTask(t);
        EventDispatch.dispatchEvent(new TaskUpdateEvent(t));
    }

    @Override
    public void completeTask(Task t) {
        if(t.isCompleted()){
            throw new ObjectFormatException("Task has already been completed!");
        }
        t.setCompleted(true);
        updateTask(t);
        EventDispatch.dispatchEvent(new TaskCompleteEvent(t));
    }
}
