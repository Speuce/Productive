package com.productive6.productive.objects.events.task;

import com.productive6.productive.objects.Task;

/**
 * Task representing when a task is completed.
 */
public class TaskCompleteEvent extends TaskEvent{
    /**
     * Construct a new task event
     *
     * @param task the task involved in this event
     */
    public TaskCompleteEvent(Task task) {
        super(task);
    }
}
