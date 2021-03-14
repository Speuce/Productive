package com.productive6.productive.objects;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * This object represents a single user task that needs to be completed.
 *
 * @author Matt Kwiatkowski
 */
@Entity(tableName = "tasks")
public class Task implements Comparable<Task>{

    @PrimaryKey(autoGenerate = true)
    private long id;

    /**
     * The user-defined name of the task
     */
    private String taskName;

    /**
     * The user-defined priority of this task
     */
    @ColumnInfo(name = "priority")
    private int priority;

    /**
     * The timestamp (milliseconds, epoch time)
     * that this task was created.
     */
    @ColumnInfo(name = "created")
    private long createdTime;

    /**
     * The day/time that this task was completed.
     * Null if not completed.
     */
    @ColumnInfo(name = "completedDay")
    private LocalDateTime completed;

    /**
     * When a given task is due
     */
    @ColumnInfo(name = "due_date")
    private LocalDate dueDate;


    /**
     * When a given task is due
     */
    @ColumnInfo(name = "difficulty")
    private int difficulty;

    /**
     * Default Constructor
     */
    @Ignore
    public Task() {
        this.priority = 1;
        this.difficulty = 1;
    }

    /**
     * Constructor for a new task.
     * @param taskName The user-defined name of the task
     * @param priority The user-defined priority of this task
     * @param createdTime The timestamp (milliseconds, epoch time)
     *                    that this task was created.
     * @param completed A flag to mark whether or not this task
     *                  has been completed.
     */
    public Task(String taskName, int priority, int difficulty, long createdTime, LocalDate dueDate, LocalDateTime completed) {
        this.taskName = taskName;
        this.priority = priority;
        this.createdTime = createdTime;
        this.completed = completed;
        this.difficulty = difficulty;
        this.dueDate = dueDate;
    }

    /**
     * Constructor for a new task without completion
     * @param taskName The user-defined name of the task
     * @param priority The user-defined priority of this task
     */
    @Ignore
    public Task(String taskName, int priority) {
        this.taskName = taskName;
        this.priority = priority;
        this.createdTime = 0;
        this.completed = null;
    }

    /**
     * Constructor for a new task without completion
     * @param taskName The user-defined name of the task
     * @param priority The user-defined priority of this task
     * @param createdTime The timestamp (milliseconds, epoch time)
     *                    that this task was created.
     */
    @Ignore
    public Task(String taskName, int priority, int difficulty, long createdTime) {
        this.taskName = taskName;
        this.priority = priority;
        this.createdTime = createdTime;
        this.difficulty = difficulty;
        this.completed = null;
//        this.dueDate = "";
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
        return completed != null;
    }

    /**
     * @param completed A flag to mark whether or not this task
     * has been completed.
     */
    public void setCompleted(LocalDateTime completed) {
        this.completed = completed;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDate getDueDate() { return dueDate; }

    public int getDifficulty() { return difficulty; }

    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    /**
     * @return The day/time that this task was completed.
     *         Null if not completed.
     */
    public LocalDateTime getCompleted() {
        return completed;
    }

    /**
     * Compares this object with another task,
     * for comparable sorting.
     */
    @Override
    public int compareTo(Task o) {
        int prioritydiff = o.getPriority()-this.getPriority();
        if(prioritydiff==0){
            return (int) (this.getCreatedTime()-o.getCreatedTime());
        }
        return prioritydiff;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Task){
            Task t2 = (Task) other;
            boolean result = t2.getId() == this.getId() &&
                    t2.getPriority() == this.getPriority() &&
                    t2.getCreatedTime() == this.getCreatedTime() &&
                    t2.getTaskName().equals(this.getTaskName()) &&
                    t2.getDifficulty() == this.getDifficulty();
            if(t2.getDueDate() != null){
                result = result && t2.getDueDate().equals(this.getDueDate());
            }else{
                result = result && this.getDueDate()==null;
            }
            if(t2.getCompleted() != null){
                result = result && t2.getCompleted().equals(this.getCompleted());
            }else{
                result = result && this.getCompleted()==null;
            }
            return result;
        }
        return false;
    }
}
