package com.productive6.productive.integration;


import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.productive6.productive.logic.adapters.ICosmeticAdapter;
import com.productive6.productive.logic.adapters.impl.DefaultCosmeticAdapter;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.rewards.impl.RewardManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.impl.PersistentTaskManager;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.logic.user.impl.PersistentSingleUserManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.enums.Difficulty;
import com.productive6.productive.objects.enums.Priority;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;
import com.productive6.productive.persistence.room.impl.InMemoryAndroidDataManager;
import com.productive6.productive.services.executor.IRunnableExecutor;
import com.productive6.productive.services.executor.impl.TestExecutor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class RewardManagerIntTest {

     IRewardManager rewardManager;

     Context mContext;

     IRunnableExecutor mRunnableExecutor;

     IUserManager userManager;

     InMemoryAndroidDataManager data;

     ITaskManager taskManager;

     @Mock
     ICosmeticAdapter adapter;

    @Before
    public void init(){ //refresh the data between each test
        EventDispatch.clear();
        mContext = InstrumentationRegistry.getInstrumentation().getContext();
        mRunnableExecutor = new TestExecutor();
        data = new InMemoryAndroidDataManager(mContext,mRunnableExecutor, mock(DefaultCosmeticAdapter.class));
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
            EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test", Priority.LOW, Difficulty.EASY)));
        }
        assertEquals("Did not have 4 XP", 4,rewardManager.getExperience());
        assertEquals("Did not have level as 4", 4,rewardManager.getLevel());

    }


    @Test
    public void testAddExperience(){

        assertEquals("Did not have 0 XP", 0,rewardManager.getExperience());

        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",Priority.HIGH,Difficulty.HARD)));
        assertEquals("Did not have 12 XP", 12,rewardManager.getExperience());

    }

    @Test
    public void testLevel(){

        assertEquals("Did not have 0 XP", 0,rewardManager.getExperience());
        for(int i = 0; i < 25; i++)
            EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",Priority.LOW,Difficulty.HARD)));

        assertEquals("Did not have 0 XP", 0,rewardManager.getExperience());
        assertEquals("Did not have level as 1", 1,rewardManager.getLevel());


    }

    @Test
    public void testMultipleLevels(){
        for(int i = 0; i< 10; i++)
            EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",Priority.HIGH,Difficulty.HARD)));

        assertEquals("Did not have 20 XP", 20,rewardManager.getExperience());
        assertEquals("Did not have level as 1", 1,rewardManager.getLevel());
    }

    @Test
    public void testAddingCoins(){

        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",Priority.HIGH,Difficulty.MEDIUM)));
        assertEquals("Did not have 6 coins", 2*3,rewardManager.getCoins());

    }

    @Test
    public void testDifferentPriority(){
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",Priority.HIGH,Difficulty.MEDIUM)));
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",Priority.MEDIUM,Difficulty.MEDIUM)));
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",Priority.LOW,Difficulty.MEDIUM)));

        assertEquals("XP did not equal expected value", 24, rewardManager.getExperience());
    }


    @Test
    public void testDifferentDifficulty(){
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",Priority.HIGH,Difficulty.HARD)));
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",Priority.MEDIUM,Difficulty.MEDIUM)));
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",Priority.LOW,Difficulty.EASY)));

        assertEquals("XP did not equal expected value", 18, rewardManager.getCoins());
    }


}
