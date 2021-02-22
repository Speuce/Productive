package com.productive6.productive;


import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.rewards.impl.RewardManager;
import com.productive6.productive.logic.rewards.impl.counter;
import com.productive6.productive.logic.user.UserManager;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;
import com.productive6.productive.objects.events.user.UserLoadedEvent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import com.productive6.productive.objects.Task;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

public class RewardManagerTest {

    IRewardManager rewardManager;

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    UserManager data;

    @Before
    public void init(){
        rewardManager = new RewardManager(data);
        EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0,0,0)));
    }

    @Test
    public void testManyEvents(){
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",4,0, true)));
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",4,0, true)));
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",4,0, true)));
        
        int x = counter.getCount();

    }


    @Test
    public void testAddExperience(){

        System.out.println("location");
        assertEquals("Did not have 0 XP", rewardManager.getExperience(),0);
        int x = counter.getCount();
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",4,0, true)));
        x = counter.getCount();
        assertEquals("Did not have 0 XP", rewardManager.getExperience(),4*4);

    }

    @Test
    public void testLevel(){
        System.out.println("location");

        assertEquals("Did not have 0 XP", rewardManager.getExperience(),0);
        int x = counter.getCount();
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",25,0, true)));
        x = counter.getCount();
        assertEquals("Did not have 0 XP", rewardManager.getExperience(),0);
        assertEquals("Did not have level as 1", rewardManager.getLevel(),1);


    }

    @Test
    public void testMultipleLevels(){
        System.out.println("location");
        int x = counter.getCount();
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",101,0, true)));
        x = counter.getCount();
        assertEquals("Did not have 4 XP", rewardManager.getExperience(),4);
        assertEquals("Did not have level as 4", rewardManager.getLevel(),4);
    }

    @Test
    public void testAddingCoins(){
        int x = counter.getCount();
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",101,0, true)));
        x = counter.getCount();
        assertEquals("Did not have level as 4", rewardManager.getCoins(),303);

    }





}
