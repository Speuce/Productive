package com.productive6.productive.integration;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.rewards.impl.StreakRewardManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.logic.task.impl.PersistentTaskManager;
import com.productive6.productive.logic.task.impl.PersistentTaskSorter;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.logic.user.impl.PersistentSingleUserManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.persistence.datamanage.IDataManager;
import com.productive6.productive.persistence.datamanage.dummy.DummyDataManager;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class StreakRewardManagerIntTest {

    IUserManager userManager;
    ITaskSorter taskSorter;
    ITaskManager taskManager;
    IRewardManager streak;
    DummyDataManager dummy;

    @Before
    public void init(){
        EventDispatch.clear();
        dummy = new DummyDataManager();
        userManager = new PersistentSingleUserManager(dummy);
        int[] config = {3,3};
        taskManager = new PersistentTaskManager(dummy,config);
        taskSorter = new PersistentTaskSorter(dummy);

        userManager.load();

        int[] rewardArr = {1,1,100,1,24};


        streak = new StreakRewardManager(userManager,taskSorter,taskManager,rewardArr);
        EventDispatch.dispatchEvent(new UserLoadedEvent(userManager.getCurrentUser()));
    }

    @Test
    public void testNotOnStreak(){

        Task tempTask = new Task("",3,3);
        taskManager.addTask(tempTask);
        taskManager.completeTask(tempTask);

        assertEquals("Value is not 1",1, streak.getCoins());

    }

    @Test
    public void testOnStreak(){

        Task tempTask1 = new Task("x",3,3);
        Task tempTask2 = new Task("y",3,3);

        taskManager.addTask(tempTask1);
        taskManager.addTask(tempTask2);
        taskManager.completeTask(tempTask1);
        taskManager.completeTask(tempTask2);

        assertEquals("Value is not 3", 3, streak.getCoins());

    }
}
