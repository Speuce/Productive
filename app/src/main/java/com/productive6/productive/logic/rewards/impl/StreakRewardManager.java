package com.productive6.productive.logic.rewards.impl;

import com.productive6.productive.logic.rewards.IStreakRewardManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.logic.util.DateUtilities;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;

import java.util.List;
import java.util.function.Consumer;


public class StreakRewardManager extends RewardManager implements IStreakRewardManager {

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
        taskSorter.getCompletedTasks(new StreakConsumer(event.getTask()));

    }

    public void onStreak(Consumer<List<Task>> run){
        taskSorter.getCompletedTasks(run);
    }

    public int getStreakLength(){return streakLength;}

    public class StreakConsumer implements Consumer<List<Task>> {

        private Task completedTask;

        public StreakConsumer(Task completedTask){
            this.completedTask = completedTask;

        }

        @Override
        public void accept(List<Task> tasks) {
            //if streak is confirmed,
            if(DateUtilities.inStreakTime(tasks, completedTask,streakLength)) {
                User person = data.getCurrentUser();
                person.setCoins(person.getCoins() + streakValueWeight);
                data.updateUser(person);
            }
        }
    }

}