package com.productive6.productive.unit;

import com.productive6.productive.dummyImp.StreakSortManagerDummy;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IRewardSpenderManager;
import com.productive6.productive.logic.rewards.IStreakRewardManager;
import com.productive6.productive.logic.rewards.impl.RewardSpenderManager;
import com.productive6.productive.logic.rewards.impl.StreakRewardManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.enums.Difficulty;
import com.productive6.productive.objects.enums.Priority;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;
import com.productive6.productive.objects.events.user.UserLoadedEvent;

import org.junit.Before;
import org.junit.Test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import com.productive6.productive.objects.Task;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RewardSpenderManagerTest {


    IRewardSpenderManager spenderManager;
    IUserManager userManager = mock(IUserManager.class);
    ITaskSorter sortManager = mock(ITaskSorter.class);
    ITaskManager taskManager = mock(ITaskManager.class);



    @Before
    public void init(){
        EventDispatch.clear();
        int[] config = {100,100,100,24};

        when(taskManager.minDifficulty()).thenReturn(3);
        when(taskManager.minPriority()).thenReturn(3);
        spenderManager = new RewardSpenderManager(userManager, sortManager, taskManager, config);
        EventDispatch.dispatchEvent(new UserLoadedEvent(new User()));
    }


    @Test
    public void TestRemoveCoins(){

        Task completedTask = new Task("task1", Priority.LOW, Difficulty.EASY);
        EventDispatch.dispatchEvent(new TaskCompleteEvent(completedTask));

        assertEquals("Did not have 100 coins after add", 100, spenderManager.getCoins());

        spenderManager.spendCoins(50);
        assertEquals("Did not have 50 coins after add", 50, spenderManager.getCoins());


    }
    @Test
    public void TestCheckCoins(){

        Task completedTask = new Task("task1", Priority.LOW, Difficulty.EASY);
        EventDispatch.dispatchEvent(new TaskCompleteEvent(completedTask));

        assertEquals("Did not have 100 coins after add", 100, spenderManager.getCoins());
        assertTrue("couldn't spend 100 coins", spenderManager.canSpend(100));
        assertTrue("couldn't spend 50 coins", spenderManager.canSpend(50));
        assertTrue("could spend 101 coins", !spenderManager.canSpend(101));

    }




}
