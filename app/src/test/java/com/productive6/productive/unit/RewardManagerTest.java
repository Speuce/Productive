package com.productive6.productive.unit;


import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.rewards.impl.RewardManager;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;
import com.productive6.productive.objects.events.user.UserLoadedEvent;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import com.productive6.productive.objects.Task;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


public class RewardManagerTest {

    static IRewardManager rewardManager;

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    static IUserManager data = mock(IUserManager.class);

    @BeforeClass
    public static void initializeManager(){
        rewardManager = new RewardManager(data,4,3,100);
    }

    @Before
    public void init(){ //refresh the data between each test
        EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0,0,0)));

    }

    @Test
    public void testManyEvents(){

        for(int i = 0; i < 1001; i++){
            EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",1,0, 0)));
        }
        assertEquals("Did not have 0 XP", 4,rewardManager.getExperience());
        assertEquals("Did not have level as 1", 40,rewardManager.getLevel());

    }


    @Test
    public void testAddExperience(){

        assertEquals("Did not have 0 XP", 0,rewardManager.getExperience());

        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",4,0, 0)));
        assertEquals("Did not have 0 XP", 16,rewardManager.getExperience());

    }

    @Test
    public void testLevel(){

        assertEquals("Did not have 0 XP", 0,rewardManager.getExperience());

        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",25,0, 0)));

        assertEquals("Did not have 0 XP", 0,rewardManager.getExperience());
        assertEquals("Did not have level as 1", 1,rewardManager.getLevel());


    }

    @Test
    public void testMultipleLevels(){

        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",101,0, 0)));

        assertEquals("Did not have 4 XP", 4,rewardManager.getExperience());
        assertEquals("Did not have level as 4", 4,rewardManager.getLevel());
    }

    @Test
    public void testAddingCoins(){

        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",101,0, 0)));
        assertEquals("Did not have level as 4", 303,rewardManager.getCoins());

    }





}
