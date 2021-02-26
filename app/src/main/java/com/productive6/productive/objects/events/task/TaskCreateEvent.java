package com.productive6.productive.objects.events.task;

import com.productive6.productive.objects.Task;

/**
 * Event called when a task has been created
 */
public class TaskCreateEvent extends TaskEvent{
    /**
     * Construct a new task event
     *
     * @param task the task involved in this event
     */
    public TaskCreateEvent(Task task) {
        super(task);
    }
}
