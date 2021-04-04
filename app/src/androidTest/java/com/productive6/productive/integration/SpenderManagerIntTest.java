package com.productive6.productive.integration;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IRewardSpenderManager;
import com.productive6.productive.logic.rewards.IStreakRewardManager;
import com.productive6.productive.logic.rewards.impl.RewardSpenderManager;
import com.productive6.productive.logic.rewards.impl.StreakRewardManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.logic.task.impl.PersistentTaskManager;
import com.productive6.productive.logic.task.impl.PersistentTaskSorter;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.logic.user.impl.PersistentSingleUserManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.enums.Difficulty;
import com.productive6.productive.objects.enums.Priority;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.persistence.datamanage.dummy.DummyDataManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SpenderManagerIntTest {

    IUserManager userManager;
    ITaskSorter taskSorter;
    ITaskManager taskManager;
    IRewardSpenderManager spenderManager;
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

        int[] rewardArr = {100,100,100,24};


        spenderManager = new RewardSpenderManager(userManager,taskSorter,taskManager,rewardArr);
        EventDispatch.dispatchEvent(new UserLoadedEvent(userManager.getCurrentUser()));
    }


    @Test
    public void TestRemoveCoins(){

        Task completedTask = new Task("task1", Priority.LOW, Difficulty.EASY);
        taskManager.addTask(completedTask);
        taskManager.completeTask(completedTask);

        assertEquals("Did not have 101 coins after add", 101, spenderManager.getCoins());

        spenderManager.spendCoins(50);
        assertEquals("Did not have 51 coins after add", 51, spenderManager.getCoins());


    }
    @Test
    public void TestCheckCoins(){

        Task completedTask = new Task("task1", Priority.LOW, Difficulty.EASY);
        taskManager.addTask(completedTask);
        taskManager.completeTask(completedTask);

        assertEquals("Did not have 101 coins after add", 101, spenderManager.getCoins());
        assertTrue("couldn't spend 101 coins", spenderManager.canSpend(101));
        assertTrue("couldn't spend 50 coins", spenderManager.canSpend(50));
        assertTrue("could spend 102 coins", !spenderManager.canSpend(102));

    }


}
