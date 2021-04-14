package com.productive6.productive.unit;

import com.productive6.productive.dummyImp.DummyTaskEvent;
import com.productive6.productive.dummyImp.StreakSortManagerDummy;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IStreakRewardManager;
import com.productive6.productive.logic.rewards.impl.StreakRewardManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.logic.util.DateUtilities;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.enums.Difficulty;
import com.productive6.productive.objects.enums.Priority;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.objects.Task;

import net.bytebuddy.asm.Advice;

import org.junit.Before;
import org.junit.Test;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.productive6.productive.logic.util.DateUtilities.strToDate;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StreakManagerTest {

    IStreakRewardManager streakRewardManager;

    IUserManager userManager = mock(IUserManager.class);

    ITaskSorter sortManager;

    ITaskManager taskManager = mock(ITaskManager.class);

    @Before
    public void init(){
        EventDispatch.clear();
        int[] config = {1,1,100,24};
        when(taskManager.minDifficulty()).thenReturn(3);
        when(taskManager.minPriority()).thenReturn(3);

        sortManager = new StreakSortManagerDummy();

        streakRewardManager = new StreakRewardManager(userManager,sortManager,taskManager, config);
        EventDispatch.dispatchEvent(new UserLoadedEvent(new User()));
    }


    @Test
    public void testStreak(){

        for(int i = 0; i < 4; i++) {
            Task temp = new Task(""+i,  Priority.LOW, Difficulty.EASY);
            temp.setCompleted(strToDate("2000-01-01 1" + i %10 + ":30"));
            EventDispatch.dispatchEvent(new DummyTaskEvent(temp));
            EventDispatch.dispatchEvent(new TaskCompleteEvent(temp));
        }

        assertEquals("Number of coins did not equal 14",14,streakRewardManager.getCoins());

    }


    @Test
    public void testNotStreak(){

        for(int i = 0; i < 4; i++) {
            Task temp = new Task(""+i, Priority.LOW, Difficulty.EASY);
            temp.setCompleted(strToDate("2000-01-01 1" + i %10 + ":30"));
            EventDispatch.dispatchEvent(new DummyTaskEvent(temp));
        }

        Task compTask = new Task("COMP",  Priority.LOW, Difficulty.EASY);
        compTask.setCompleted(strToDate("2000-01-02 20:30"));

        EventDispatch.dispatchEvent(new TaskCompleteEvent(compTask));
        assertEquals("Number of coins did not equal 1",1,streakRewardManager.getCoins());

    }

    @Test
    public void testSuddenStreak(){

        for(int i = 0; i < 4; i++) {
            Task temp = new Task(""+i,  Priority.LOW, Difficulty.EASY);
            temp.setCompleted(DateUtilities.strToDate("2000-01-01 1" + i %10 + ":30"));
            EventDispatch.dispatchEvent(new DummyTaskEvent(temp));
        }

        Task compTask = new Task("COMP",  Priority.LOW, Difficulty.EASY);
        compTask.setCompleted(DateUtilities.strToDate("2000-01-01 20:30"));

        EventDispatch.dispatchEvent(new TaskCompleteEvent(compTask));
        assertEquals("Number of coins did not equal 5",5,streakRewardManager.getCoins());

    }

}
