package com.productive6.productive.logic.rewards.impl;

import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;


public class StreakRewardManager extends RewardManager {

    protected ITaskSorter taskSorter;
    private int streakValueWeight;
    private int streakLength;

    public StreakRewardManager(IUserManager data, ITaskSorter taskSorter, ITaskManager taskManager, int[] configValues){
        super(data,taskManager,configValues[0],configValues[1],configValues[2]);
        this.streakValueWeight = configValues[3];
        this.streakLength = configValues[4];
        this.taskSorter = taskSorter;
    }

    protected void taskCompleted(TaskCompleteEvent event){

        super.taskCompleted(event);
        taskSorter.getCompletedTasks(new StreakConsumer(data,event.getTask(),streakValueWeight,streakLength));

    }

    public class StreakConsumer implements Consumer<List<Task>> {

        private IUserManager data;
        private Task completedTask;
        private int streakWeight;
        private int streakLength;

        public StreakConsumer(IUserManager data, Task completedTask, int streakWeight, int streakLength){
            this.data = data;
            this.completedTask = completedTask;
            this.streakWeight = streakWeight;
            this.streakLength = streakLength;
        }

        @Override
        public void accept(List<Task> tasks) {

            boolean onStreak = false;

            Iterator<Task> taskIterator = tasks.iterator();
            while(taskIterator.hasNext() && !onStreak){
                Task curr = taskIterator.next();
                //calculate period
                Duration timeDifference = Duration.between(curr.getCompleted(),completedTask.getCompleted());
                int hours = (int) timeDifference.toHours();
                if(hours <= streakLength && curr.getId() != completedTask.getId()) //check if with streak window and different tasks
                    onStreak = true;

            }

            //if streak is confirmed,
            if(onStreak) {
                User person = data.getCurrentUser();
                person.setCoins(person.getCoins() + streakWeight);
                data.updateUser(person);
            }
        }
    }

}