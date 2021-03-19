package com.productive6.productive.integration;


import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.rewards.impl.RewardManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.impl.PersistentTaskManager;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.logic.user.impl.PersistentSingleUserManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.persistence.datamanage.impl.InMemoryAndroidDataManager;
import com.productive6.productive.services.executor.IRunnableExecutor;
import com.productive6.productive.services.executor.impl.TestExecutor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class RewardManagerIntTest {

     IRewardManager rewardManager;

     Context mContext;

     IRunnableExecutor mRunnableExecutor;

     IUserManager userManager;

     InMemoryAndroidDataManager data;

     ITaskManager taskManager;

    @Before
    public void init(){ //refresh the data between each test
        EventDispatch.clear();
        mContext = InstrumentationRegistry.getInstrumentation().getContext();
        mRunnableExecutor = new TestExecutor();
        data = new InMemoryAndroidDataManager(mContext,mRunnableExecutor);
        data.init();
        userManager = new PersistentSingleUserManager(data);
        int[] config = {3,3};
        taskManager = new PersistentTaskManager(data,config);
        rewardManager = new RewardManager(userManager,taskManager,4,3,100);
        userManager.load();
    }

    @Test
    public void testManyEvents(){

        for(int i = 0; i < 101; i++){
            EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",3,3)));
        }
        assertEquals("Did not have 4 XP", 4,rewardManager.getExperience());
        assertEquals("Did not have level as 4", 4,rewardManager.getLevel());

    }


    @Test
    public void testAddExperience(){

        assertEquals("Did not have 0 XP", 0,rewardManager.getExperience());

        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",1,1)));
        assertEquals("Did not have 12 XP", 12,rewardManager.getExperience());

    }

    @Test
    public void testLevel(){

        assertEquals("Did not have 0 XP", 0,rewardManager.getExperience());
        for(int i = 0; i < 25; i++)
            EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",3,1)));

        assertEquals("Did not have 0 XP", 0,rewardManager.getExperience());
        assertEquals("Did not have level as 1", 1,rewardManager.getLevel());


    }

    @Test
    public void testMultipleLevels(){
        for(int i = 0; i< 10; i++)
            EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",1,1)));

        assertEquals("Did not have 20 XP", 20,rewardManager.getExperience());
        assertEquals("Did not have level as 1", 1,rewardManager.getLevel());
    }

    @Test
    public void testAddingCoins(){

        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",1,2)));
        assertEquals("Did not have 6 coins", 2*3,rewardManager.getCoins());

    }

    @Test
    public void testDifferentPriority(){
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",1,101)));
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",2,101)));
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",3,101)));

        assertEquals("XP did not equal expected value", 24, rewardManager.getExperience());
    }


    @Test
    public void testDifferentDifficulty(){
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",1,1)));
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",2,2)));
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",3,3)));

        assertEquals("XP did not equal expected value", 18, rewardManager.getCoins());
    }


}
