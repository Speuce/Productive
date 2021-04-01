package com.productive6.productive.logic.task.impl;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.exceptions.PersistentIDAssignmentException;
import com.productive6.productive.logic.exceptions.ObjectFormatException;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;
import com.productive6.productive.objects.events.task.TaskCreateEvent;
import com.productive6.productive.objects.events.task.TaskUpdateEvent;
import com.productive6.productive.persistence.datamanage.IDataManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A production-grade and persistent implementation of the
 * {@link ITaskManager} interface
 */
public class PersistentTaskManager implements ITaskManager{


    private final IDataManager data;
    private final int minDifficulty;
    private final int minPriority;

    public PersistentTaskManager(IDataManager data, int[] configValues) {
        this.data = data;
        minPriority = configValues[0];
        minDifficulty = configValues[1];
    }


    /**
     * Ensures that all the fields of the given task are valid.
     * Throws exceptions otherwise.
     * @param t the task to validate.
     */
    private void validateTask(Task t){
        if(t.getPriority().ordinal() < 0){
            throw new ObjectFormatException("A priority of < 0 is not supported!");
        }
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
        }
        validateTask(t);
        data.task().insertTask(t, () ->{
            EventDispatch.dispatchEvent(new TaskCreateEvent(t));
        });
    }


    @Override
    public void updateTask(Task t) {
        if(t.getId() == 0){
            throw new PersistentIDAssignmentException();
        }
        validateTask(t);
        data.task().updateTask(t,() -> EventDispatch.dispatchEvent(new TaskUpdateEvent(t)));

    }

    @Override
    public void completeTask(Task t) {
        if(t.isCompleted()){
            throw new ObjectFormatException("Task has already been completed!");
        }
        t.setCompleted(LocalDateTime.now());
        updateTask(t);
        EventDispatch.dispatchEvent(new TaskCompleteEvent(t));
    }

    @Override
    public int minPriority() {
        return minPriority;
    }

    @Override
    public int minDifficulty() {
        return minDifficulty;
    }
}
