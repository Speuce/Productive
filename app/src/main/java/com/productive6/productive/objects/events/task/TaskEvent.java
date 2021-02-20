package com.productive6.productive.objects.events.task;

import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.ProductiveEvent;

/**
 * Represents any task involving an event
 */
public abstract class TaskEvent extends ProductiveEvent {

    /**
     * The task involved in this task event
     */
    private Task task;

    /**
     * Construct a new task event
     * @param task the task involved in this event
     */
    public TaskEvent(Task task) {
        this.task = task;
    }

    /**
     * @return The task involved in this task event
     */
    public Task getTask() {
        return task;
    }

    /**
     * @param task The task involved in this task event
     */
    public void setTask(Task task) {
        this.task = task;
    }
}
