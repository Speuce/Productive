package com.productive6.productive.dummyImp;

import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.task.TaskEvent;

/**
 * Task representing when a task is completed.
 */
public class DummyTaskEvent extends TaskEvent {
    /**
     * Construct a new task event
     *
     * @param task the task involved in this event
     */
    public DummyTaskEvent(Task task) {
        super(task);
    }

}
