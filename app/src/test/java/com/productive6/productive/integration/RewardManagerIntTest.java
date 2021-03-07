package com.productive6.productive.integration;


import android.content.Context;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.rewards.impl.RewardManager;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.logic.user.impl.PersistentSingleUserManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;
import com.productive6.productive.objects.events.task.TaskCreateEvent;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.objects.events.user.UserUpdateEvent;
import com.productive6.productive.persistence.datamanage.impl.InMemoryAndroidDataManager;
import com.productive6.productive.persistence.executor.IRunnableExecutor;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


public class RewardManagerIntTest {

     IRewardManager rewardManager;

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock
     Context mContext = mock(Context.class);
    @Mock
     IRunnableExecutor mRunnableExecutor = mock(IRunnableExecutor.class);

     IUserManager userManager;

     InMemoryAndroidDataManager data;

    @BeforeClass
    public static void initializeManager(){

    }

    @Before
    public void init(){ //refresh the data between each test
        data = new InMemoryAndroidDataManager(mContext,mRunnableExecutor);
        data.init();
        userManager = new PersistentSingleUserManager(data);
        rewardManager = new RewardManager(userManager,4,3,100);
        userManager.load();
    }

    @Test
    public void testManyEvents(){
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(UserLoadedEvent e){
                for(int i = 0; i < 1001; i++){
                    EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",1,0, 0)));
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

                EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",4,0, 0)));

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

                EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",25,0, 0)));

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
                EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",101,0, 0)));

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
                EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",101,0, 0)));
                assertEquals("Did not have level as 4", 303,rewardManager.getCoins());
            }
        });
    }

}
