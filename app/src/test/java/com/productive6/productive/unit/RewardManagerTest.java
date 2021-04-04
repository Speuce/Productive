package com.productive6.productive.unit;


import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.rewards.impl.RewardManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.enums.Difficulty;
import com.productive6.productive.objects.enums.Priority;
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
import static org.mockito.Mockito.when;


public class RewardManagerTest {

    static IRewardManager rewardManager;

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    static IUserManager data = mock(IUserManager.class);

    @Mock
    static ITaskManager taskManager = mock(ITaskManager.class);

    @BeforeClass
    public static void initializeManager(){
        int[] config = {4,3,100};
        EventDispatch.clear();
        when(taskManager.minDifficulty()).thenReturn(3);
        when(taskManager.minPriority()).thenReturn(3);
        rewardManager = new RewardManager(data, taskManager,config[0],config[1],config[2]);
    }

    @Before
    public void init(){ //refresh the data between each test
        EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0,0,0)));

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
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",Priority.MEDIUM, Difficulty.MEDIUM)));
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",Priority.LOW,Difficulty.MEDIUM)));

        assertEquals("XP did not equal expected value", 24, rewardManager.getExperience());
    }


    @Test
    public void testDifferentDifficulty(){
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",Priority.HIGH,Difficulty.HARD)));
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",Priority.MEDIUM, Difficulty.MEDIUM)));
        EventDispatch.dispatchEvent(new TaskCompleteEvent(new Task("test",Priority.LOW,Difficulty.EASY)));

        assertEquals("XP did not equal expected value", 18, rewardManager.getCoins());
    }

}
