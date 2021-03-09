package com.productive6.productive.logic.rewards.impl;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;

import java.util.List;
import java.util.function.Consumer;


public class StreakRewardManager extends RewardManager {

    protected ITaskManager taskManager;

    public StreakRewardManager(IUserManager data, ITaskManager taskManager, int[] rewardWeights){
        super(data,rewardWeights[0],rewardWeights[1],rewardWeights[2]);
        this.taskManager = taskManager;
    }

    protected void taskCompleted(TaskCompleteEvent event){

        taskManager.getCompletedTasks(new StreakConsumer(data,event.getTask()));

        super.taskCompleted(event);

    }

    public class StreakConsumer implements Consumer<List<Task>> {

        private IUserManager data;
        private Task completedTask;

        public StreakConsumer(IUserManager data, Task completedTask){
            this.data = data;
            this.completedTask = completedTask;
        }

        @Override
        public void accept(List<Task> tasks) {
            /**
             * INSERT TIMING HERE
             * remove get diff
             */
            User person = data.getCurrentUser();
            person.setCoins(person.getCoins()+1);
            data.updateUser(person);
        }
    }

}