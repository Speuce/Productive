package com.productive6.productive.objects;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @NotNull
    private LocalDateTime createdTime;

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
     * Coins earned from the completion of the task (if applicable)
     */
    @ColumnInfo(name = "coins")
    private int coinsEarned;

    /**
     * XP earned from the completion of the task (if applicable)
     */
    @ColumnInfo(name = "xp")
    private int xpEarned;

    /**
     * Default Constructor
     */
    @Ignore
    public Task() {
        this.priority = 1;
        this.difficulty = 1;
        createdTime = LocalDateTime.now();
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
    public Task(String taskName, int priority, int difficulty, LocalDateTime createdTime, LocalDate dueDate, LocalDateTime completed) {
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
    public Task(String taskName, int priority, int difficulty) {
        this.taskName = taskName;
        this.difficulty = difficulty;
        this.priority = priority;
        this.createdTime = LocalDateTime.now();
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
    public Task(String taskName, int priority, int difficulty,@NotNull LocalDateTime createdTime) {
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

    public String getPriorityInString() {
        String priorityInString = "";

        if (getPriority() == 1) {
            priorityInString = "Hard";
        } else if (getPriority() == 2) {
            priorityInString = "Medium";
        } else {
            priorityInString = "Easy";
        }
        return priorityInString;
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

    @NotNull
    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }


    public void setCreatedTime(@NotNull  LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDate getDueDate() { return dueDate; }

    public int getDifficulty() { return difficulty; }


    public String getDifficultyInString() {
        String difficultyInString = "";

        if (getDifficulty() == 1) {
            difficultyInString = "Hard";
        } else if (getDifficulty() == 2) {
            difficultyInString = "Medium";
        } else {
            difficultyInString = "Easy";
        }
        return difficultyInString;
    }

    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    public int getCoinsEarned() {
        return coinsEarned;
    }

    public void setCoinsEarned(int coinsEarned) {
        this.coinsEarned = coinsEarned;
    }

    public int getXpEarned() {
        return xpEarned;
    }

    public void setXpEarned(int xpEarned) {
        this.xpEarned = xpEarned;
    }

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
            return this.getCreatedTime().compareTo(o.getCreatedTime());
        }
        return prioritydiff;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Task){
            Task t2 = (Task) other;
            boolean result = t2.getId() == this.getId() &&
                    t2.getPriority() == this.getPriority() &&
                    t2.getCreatedTime().equals(this.getCreatedTime()) &&
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
