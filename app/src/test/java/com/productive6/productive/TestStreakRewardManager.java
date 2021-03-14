package com.productive6.productive;

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
import com.productive6.productive.persistence.datamanage.dummy.DummyUserPersistenceManager;
import com.productive6.productive.persistence.datamanage.impl.InMemoryAndroidDataManager;
import com.productive6.productive.persistence.datamanage.impl.TaskPersistenceManager;
import com.productive6.productive.persistence.datamanage.impl.UserPersistenceManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

public class TestStreakRewardManager {

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
        taskManager = new PersistentTaskManager(dummy);
        taskSorter = new PersistentTaskSorter(dummy);

        userManager.load();

        int[] rewardArr = {1,1,100};
        int[] streakArr = {1,24};

        streak = new StreakRewardManager(userManager,taskSorter,rewardArr,streakArr);
        EventDispatch.dispatchEvent(new UserLoadedEvent(userManager.getCurrentUser()));
    }

    @Test
    public void testNotOnStreak(){

        Task tempTask = new Task("",1,1,0);
        taskManager.addTask(tempTask);
        taskManager.completeTask(tempTask);
        EventDispatch.dispatchEvent(new TaskCompleteEvent(tempTask));

        assertEquals("Value is not 1",1, streak.getCoins());

    }

    @Test
    public void testOnStreak(){

        Task tempTask1 = new Task("x",10,10,0);
        Task tempTask2 = new Task("y",10,10,0);

        taskManager.addTask(tempTask1);
        taskManager.addTask(tempTask2);
        taskManager.completeTask(tempTask1);
        taskManager.completeTask(tempTask2);

        assertEquals("Value is not 1", 21, streak.getCoins());

    }
}
