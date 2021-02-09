package com.productive6.productive.objects;

/**
 * This object represents a single user task that needs to be completed.
 *
 * @author Matt Kwiatkowski
 */
public class Task {

    /**
     * The user-defined name of the task
     */
    private String taskName;

    /**
     * The user-defined priority of this task
     */
    private int priority;

    /**
     * The timestamp (milliseconds, epoch time)
     * that this task was created.
     */
    private long createdTime;

    /**
     * A flag to mark whether or not this task
     * has been completed.
     */
    private boolean completed;

    /**
     * Constructor for a new task.
     * @param taskName The user-defined name of the task
     * @param priority The user-defined priority of this task
     * @param createdTime The timestamp (milliseconds, epoch time)
     *                    that this task was created.
     * @param completed A flag to mark whether or not this task
     *                  has been completed.
     */
    public Task(String taskName, int priority, long createdTime, boolean completed) {
        this.taskName = taskName;
        this.priority = priority;
        this.createdTime = createdTime;
        this.completed = completed;
    }

    /**
     * Constructor for a new task without completion
     * @param taskName The user-defined name of the task
     * @param priority The user-defined priority of this task
     * @param createdTime The timestamp (milliseconds, epoch time)
     *                    that this task was created.
     */
    public Task(String taskName, int priority, long createdTime) {
        this.taskName = taskName;
        this.priority = priority;
        this.createdTime = createdTime;
        this.completed = false;
    }

    /**
     * @return an {@code String} representing
     * the user-defined name of the task
     */
    public String getTaskName() {
        return taskName;
    }


    /**
     * @param taskName The user-defined name of the task
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * @return The user-defined priority of this task
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param priority The user-defined priority of this task
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * @return {@code true} if this task has already been marked as completed,
     * false otherwise.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * @param completed A flag to mark whether or not this task
     * has been completed.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
