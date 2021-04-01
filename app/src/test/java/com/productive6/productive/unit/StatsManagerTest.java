package com.productive6.productive.unit;

import com.productive6.productive.logic.statstics.ICoinsStatsManager;
import com.productive6.productive.logic.statstics.ITaskStatsManager;
import com.productive6.productive.logic.statstics.IXPStatsManager;
import com.productive6.productive.logic.statstics.impl.StatsManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.enums.Difficulty;
import com.productive6.productive.objects.enums.Priority;
import com.productive6.productive.persistence.datamanage.IDataManager;
import com.productive6.productive.persistence.datamanage.dummy.DummyDataManager;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;


public class StatsManagerTest {

    private ITaskStatsManager taskManager;

    private IXPStatsManager xpManager;

    private ICoinsStatsManager coinManager;

    private IDataManager data;

    @Before
    public void init(){
        data = new DummyDataManager();
        data.init();
        taskManager = new StatsManager(data);
        xpManager = (StatsManager)taskManager;
        coinManager = (StatsManager)taskManager;
    }

    @Test
    public void testAverageTasks(){
        Task testData = new Task("name", Priority.HIGH, Difficulty.HARD, LocalDateTime.now());
        testData.setCompleted(LocalDateTime.now());
        Task testData2 = new Task("name", Priority.HIGH, Difficulty.HARD, LocalDateTime.now());
        testData2.setCompleted(LocalDateTime.now());
        Task testData3 = new Task("name", Priority.HIGH, Difficulty.HARD, LocalDateTime.now().minusDays(1));
        testData3.setCompleted(LocalDateTime.now());
        data.task().insertTask(testData, () ->{});
        data.task().insertTask(testData2, () ->{});
        data.task().insertTask(testData3, () ->{});
        taskManager.getAverageTasksCompletedDaily(aFloat -> {
            assertEquals("Calculating average was incorrect",1.5, (double)aFloat, 0.01);
        });
    }

    @Test
    public void testGetCompletedTasksByDay(){
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        Task testData = new Task("name", Priority.HIGH, Difficulty.HARD, LocalDateTime.now());
        testData.setCompleted(LocalDateTime.now());
        Task testData2 = new Task("name", Priority.HIGH, Difficulty.HARD, LocalDateTime.now());
        testData2.setCompleted(LocalDateTime.now());
        Task testData3 = new Task("name", Priority.HIGH, Difficulty.HARD, yesterday);
        testData3.setCompleted(yesterday);
        data.task().insertTask(testData, () ->{});
        data.task().insertTask(testData2, () ->{});
        data.task().insertTask(testData3, () ->{});

        AtomicInteger daysCounted = new AtomicInteger(0);
        taskManager.getTasksCompletedPastDays(1, dayIntTuple -> {
            if(dayIntTuple.getDate().equals(yesterday.toLocalDate())){
                daysCounted.incrementAndGet();
                assertEquals("Couldn't count tasks completed previous day!", 1, (int)dayIntTuple.getNumber());
            }else if(dayIntTuple.getDate().equals(LocalDate.now())){
                daysCounted.incrementAndGet();
                assertEquals("Couldn't count tasks completed today day!", 2, (int)dayIntTuple.getNumber());
            }else{
                fail("Got an unaccounted for date while searching past days completions: " + dayIntTuple.getDate().toString());
            }
        });
        assertEquals("Searching past days completions missed a day!",daysCounted.get(), 2);
    }

    /**
     * Tests filtering out specific days with getting completed tasks
     */
    @Test
    public void testGetCompletedTasksByDayFilter(){
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        Task testData = new Task("name", Priority.HIGH, Difficulty.HARD, LocalDateTime.now());
        testData.setCompleted(LocalDateTime.now());
        Task testData2 = new Task("name", Priority.HIGH, Difficulty.HARD, LocalDateTime.now());
        testData2.setCompleted(LocalDateTime.now());
        Task testData3 = new Task("name", Priority.HIGH, Difficulty.HARD, yesterday);
        testData3.setCompleted(yesterday);
        data.task().insertTask(testData, () ->{});
        data.task().insertTask(testData2, () ->{});
        data.task().insertTask(testData3, () ->{});


        AtomicInteger daysCounted = new AtomicInteger(0);
        taskManager.getTasksCompletedPastDays(0, dayIntTuple -> {
            if(dayIntTuple.getDate().equals(LocalDate.now())){
                daysCounted.incrementAndGet();
                assertEquals("Couldn't count tasks completed today day!", 2, (int)dayIntTuple.getNumber());
            }else{
                fail("Got an unaccounted for date while searching past days completions: " + dayIntTuple.getDate().toString());
            }
        });
        assertEquals("Searching past days completions filtered out the wrong day!!",daysCounted.get(), 1);
    }

    @Test
    public void testFirstDay(){
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        Task testData = new Task("name", Priority.HIGH, Difficulty.HARD, LocalDateTime.now());
        Task testData3 = new Task("name", Priority.HIGH, Difficulty.HARD, yesterday);
        data.task().insertTask(testData, () ->{});
        data.task().insertTask(testData3, () ->{});

        taskManager.getFirstTaskDay(localDate -> {
            assertEquals("Get First Task day didn't behave as expected!", yesterday.toLocalDate(), localDate);
        });
    }

    @Test
    public void testCoinsAllTime(){
        Task testData = new Task("name", Priority.HIGH, Difficulty.HARD, LocalDateTime.now());
        testData.setCompleted(LocalDateTime.now());
        testData.setCoinsEarned(5);
        Task testData2 = new Task("name", Priority.HIGH, Difficulty.HARD, LocalDateTime.now());
        testData2.setCompleted(LocalDateTime.now().minusDays(1));
        testData2.setCoinsEarned(10);
        Task testData3 = new Task("name", Priority.HIGH, Difficulty.HARD, LocalDateTime.now());
        testData3.setCoinsEarned(69);
        data.task().insertTask(testData, () ->{});
        data.task().insertTask(testData2, () ->{});
        data.task().insertTask(testData3, () ->{});

        coinManager.getCoinsEarnedAllTime(in ->{
            assertEquals("Summing Coins Earned All Time gave an unexpected result!", 15, (int)in);
        });
    }

    @Test
    public void testXpAllTime(){
        Task testData = new Task("name", Priority.HIGH, Difficulty.HARD, LocalDateTime.now());
        testData.setCompleted(LocalDateTime.now());
        testData.setXpEarned(5);
        Task testData2 = new Task("name", Priority.HIGH, Difficulty.HARD, LocalDateTime.now());
        testData2.setCompleted(LocalDateTime.now().minusDays(1));
        testData2.setXpEarned(20);
        Task testData3 = new Task("name", Priority.HIGH, Difficulty.HARD, LocalDateTime.now());
        testData3.setXpEarned(69);
        data.task().insertTask(testData, () ->{});
        data.task().insertTask(testData2, () ->{});
        data.task().insertTask(testData3, () ->{});
        xpManager.getXPEarnedAllTime(in ->{
            assertEquals("Summing Coins Earned All Time gave an unexpected result!", 25, (int)in);
        });
    }
}
