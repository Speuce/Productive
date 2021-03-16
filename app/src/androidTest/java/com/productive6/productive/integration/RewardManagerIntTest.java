package com.productive6.productive.integration;


import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.rewards.impl.RewardManager;
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


    @Before
    public void init(){ //refresh the data between each test
        EventDispatch.clear();
        mContext = InstrumentationRegistry.getInstrumentation().getContext();
        mRunnableExecutor = new TestExecutor();
        data = new InMemoryAndroidDataManager(mContext,mRunnableExecutor);
        data.init();
        userManager = new PersistentSingleUserManager(data);

        rewardManager = new RewardManager(userManager, new PersistentTaskManager(data), 4,3,100);
        userManager.load();
    }

    @Test
    public void testManyEvents(){
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(UserLoadedEvent e){
                for(int i = 0; i < 1001; i++){
                    EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",1,0)));
                }
                assertEquals("Did not have 0 XP", 4,rewardManager.getExperience());
                assertEquals("Did not have level as 1", 40,rewardManager.getLevel());
            }
        });
    }


    @Test
    public void testAddExperience(){
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(UserLoadedEvent e){
                assertEquals("Did not have 0 XP", 0,rewardManager.getExperience());

                EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",4,0)));

                assertEquals("Did not have 0 XP", 16,rewardManager.getExperience());
            }
        });
    }

    @Test
    public void testLevel(){
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(UserLoadedEvent e){
                assertEquals("Did not have 0 XP", 0,rewardManager.getExperience());

                EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",25,0)));

                assertEquals("Did not have 0 XP", 0,rewardManager.getExperience());
                assertEquals("Did not have level as 1", 1,rewardManager.getLevel());
            }
        });

    }

    @Test
    public void testMultipleLevels(){
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(UserLoadedEvent e){
                EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",101,0)));

                assertEquals("Did not have 4 XP", 4,rewardManager.getExperience());
                assertEquals("Did not have level as 4", 4,rewardManager.getLevel());
            }
        });

    }

    @Test
    public void testAddingCoins(){
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(UserLoadedEvent e){
                EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",101,0)));
                assertEquals("Did not have level as 4", 303,rewardManager.getCoins());
            }
        });
    }

}
