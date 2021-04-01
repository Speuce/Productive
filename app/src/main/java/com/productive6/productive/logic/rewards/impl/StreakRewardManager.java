package com.productive6.productive.logic.rewards.impl;

import com.productive6.productive.logic.rewards.IStreakRewardManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;


public class StreakRewardManager extends RewardManager implements IStreakRewardManager {

    protected ITaskSorter taskSorter;
    private int streakLength;

    public StreakRewardManager(IUserManager data, ITaskSorter taskSorter, ITaskManager taskManager, int[] configValues){
        super(data,taskManager,configValues[0],configValues[1],configValues[2]);
        this.streakLength = configValues[3];
        this.taskSorter = taskSorter;
    }

    /**
     * Overload of what happens when a task completed in RewardManager
     * @param event the event that was completed
     */
    protected void taskCompleted(TaskCompleteEvent event){

        super.taskCompleted(event);
        taskSorter.getCompletedTasks(new StreakConsumer(event.getTask()));

    }


    public class StreakConsumer implements Consumer<List<Task>> {

        private Task completedTask;

        public StreakConsumer(Task completedTask){
            this.completedTask = completedTask;

        }

        @Override
        public void accept(List<Task> tasks) {
            //if streak is confirmed,
            int streakCount = streakCount(tasks, completedTask.getCompleted());

            if(streakCount > 0){
                person.setCoins(person.getCoins() + streakCount);
                data.updateUser(person);
            }
        }
    }

    /**
     * Counts the current streak length in a task list
     * @param tasks the list of tasks to search for streaking tasks in
     * @param currTime The current time
     * @return The size of the current streak
     */
    public int streakCount(List<Task> tasks, LocalDateTime currTime){

        int count = 0;

        Iterator<Task> taskIterator = tasks.iterator();
        while(taskIterator.hasNext()){
            Task curr = taskIterator.next();
            //calculate period
            Duration timeDifference = Duration.between(curr.getCompleted(),currTime);
            int hours = (int) timeDifference.toHours();
            if(hours <= streakLength && curr.isCompleted()) //check if with streak window and different tasks
                count++;

        }
        return count;
    }

    //returns true if on streak
    public boolean onStreak(List<Task> tasks, LocalDateTime currTime){
        return (streakCount(tasks, currTime) > 0);
    }

    //String representation of streak
    public String getStreakAsString(List<Task> tasks, LocalDateTime currTime){
        return "Streak Bonus: " + streakCount(tasks, currTime);
    }

}