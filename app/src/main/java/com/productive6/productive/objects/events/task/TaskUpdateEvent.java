package com.productive6.productive.objects.events.task;

import com.productive6.productive.objects.Task;

/**
 * Event Called when any information of a task is updated.
 */
public class TaskUpdateEvent extends TaskEvent{
    /**
     * Construct a new task event
     *
     * @param task the task involved in this event
     */
    public TaskUpdateEvent(Task task) {
        super(task);
    }
}
