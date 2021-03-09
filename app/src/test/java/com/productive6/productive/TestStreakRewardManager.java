package com.productive6.productive;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.impl.StreakRewardManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.impl.PersistentTaskManager;
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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

public class TestStreakRewardManager {

    IUserManager userManager;

    ITaskManager taskManager;

    StreakRewardManager streak;

    DummyDataManager dummy;

    @Before
    public void init(){

        dummy = new DummyDataManager();
        userManager = new PersistentSingleUserManager(dummy);
        taskManager = new PersistentTaskManager(dummy);

        userManager.load();

        int[] arr = {1,1,100};
        streak = new StreakRewardManager(userManager,taskManager,arr);
        EventDispatch.dispatchEvent(new UserLoadedEvent(userManager.getCurrentUser()));
    }

    @Test
    public void test1(){

        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("",0,0,0)));

        assertEquals("VALUE IS 5", 1, streak.getCoins());

    }

}
