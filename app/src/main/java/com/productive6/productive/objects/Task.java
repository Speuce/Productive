package com.productive6.productive.objects;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.productive6.productive.objects.enums.Category;
import com.productive6.productive.objects.enums.Difficulty;
import com.productive6.productive.objects.enums.Priority;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This object represents a single user task that needs to be completed.
 *
 * @author Matt Kwiatkowski
 */
@Entity(tableName = "tasks")
public class Task implements Comparable<Task> {

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
    private Priority priority;

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
     * Difficulty of the task
     */
    @ColumnInfo(name = "difficulty")
    private Difficulty difficulty;

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
     * Category of the task
     */
    @ColumnInfo(name = "category")
    private Category category;

    /**
     * Default Constructor
     */
    @Ignore
    public Task() {
        this.priority = Priority.MEDIUM;
        this.difficulty = Difficulty.MEDIUM;
        this.category = Category.UNCATEGORIZED;
        createdTime = LocalDateTime.now();
    }

    /**
     * Constructor for a new task.
     *
     * @param taskName    The user-defined name of the task
     * @param priority    The user-defined priority of this task
     * @param createdTime The timestamp (milliseconds, epoch time)
     *                    that this task was created.
     * @param completed   A flag to mark whether or not this task
     *                    has been completed.
     */
    public Task(String taskName, Priority priority, Difficulty difficulty, @NotNull LocalDateTime createdTime, LocalDate dueDate, LocalDateTime completed, Category category) {
        this.taskName = taskName;
        this.priority = priority;
        this.createdTime = createdTime;
        this.completed = completed;
        this.difficulty = difficulty;
        this.dueDate = dueDate;
        this.category = category;
    }

    /**
     * Constructor for a new task without category.
     *
     * @param taskName    The user-defined name of the task
     * @param priority    The user-defined priority of this task
     * @param createdTime The timestamp (milliseconds, epoch time)
     *                    that this task was created.
     * @param completed   A flag to mark whether or not this task
     *                    has been completed.
     */
    @Ignore
    public Task(String taskName, Priority priority, Difficulty difficulty, @NotNull LocalDateTime createdTime, LocalDate dueDate, LocalDateTime completed) {
        this.taskName = taskName;
        this.priority = priority;
        this.createdTime = createdTime;
        this.completed = completed;
        this.difficulty = difficulty;
        this.dueDate = dueDate;
        this.category = Category.UNCATEGORIZED;
    }

    /**
     * Constructor for a new task without completion
     *
     * @param taskName    The user-defined name of the task
     * @param priority    The user-defined priority of this task
     * @param createdTime The timestamp (milliseconds, epoch time)
     *                    that this task was created.
     */
    @Ignore
    public Task(String taskName, Priority priority, Difficulty difficulty, @NotNull LocalDateTime createdTime) {
        this(taskName, priority, difficulty, createdTime, null, null, Category.UNCATEGORIZED);
    }

    /**
     * Constructor for a new task without completion
     *
     * @param taskName The user-defined name of the task
     * @param priority The user-defined priority of this task
     */
    @Ignore
    public Task(String taskName, Priority priority, Difficulty difficulty) {
        this.taskName = taskName;
        this.difficulty = difficulty;
        this.priority = priority;
        this.createdTime = LocalDateTime.now();
        this.completed = null;
        this.dueDate = null;
        this.category = Category.UNCATEGORIZED;
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
    public Priority getPriority() {
        return priority;
    }

    /**
     * @param priority The user-defined priority of this task
     */
    public void setPriority(Priority priority) {
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
     *                  has been completed.
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

    public void setCreatedTime(@NotNull LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

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

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    /**
     * @return The day/time that this task was completed.
     * Null if not completed.
     */
    public LocalDateTime getCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", priority=" + priority +
                ", createdTime=" + createdTime +
                ", completed=" + completed +
                ", dueDate=" + dueDate +
                ", difficulty=" + difficulty +
                ", category=" + category +
                ", coinsEarned=" + coinsEarned +
                ", xpEarned=" + xpEarned +
                '}';
    }

    /**
     * Compares this object with another task,
     * for comparable sorting.
     */
    @Override
    public int compareTo(Task o) {
        int prioritydiff = o.getPriority().ordinal() - this.getPriority().ordinal();
        if (prioritydiff == 0) {
            return this.getCreatedTime().compareTo(o.getCreatedTime());
        }
        return prioritydiff;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Task) {
            Task t2 = (Task) other;
            boolean result = t2.getId() == this.getId() &&
                    t2.getPriority() == this.getPriority() &&
                    t2.getCreatedTime().equals(this.getCreatedTime()) &&
                    t2.getTaskName().equals(this.getTaskName()) &&
                    t2.getDifficulty() == this.getDifficulty() &&
                    t2.getCategory() == this.getCategory();
            if (t2.getDueDate() != null) {
                result = result && t2.getDueDate().equals(this.getDueDate());
            } else {
                result = result && this.getDueDate() == null;
            }
            if (t2.getCompleted() != null) {
                result = result && t2.getCompleted().equals(this.getCompleted());
            } else {
                result = result && this.getCompleted() == null;
            }
            return result;
        }
        return false;
    }
}
