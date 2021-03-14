package com.productive6.productive.unit;

import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.logic.task.impl.PersistentTaskSorter;
import com.productive6.productive.objects.Task;
import com.productive6.productive.persistence.datamanage.dummy.DummyDataManager;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Tests logic-layer task sorting by the task Manager
 */
public class TaskSorterTest {

    private ITaskSorter taskSorter;

    private DummyDataManager data;

    @Before
    public void init(){
        data = new DummyDataManager();
        taskSorter = new PersistentTaskSorter(data);
    }


    /**
     * Tests that sorting tasks by priority functions as expected (higher priority first)
     */
    @Test
    public void testGetByPriority(){
        data.task().insertTask(new Task("task", 5, 1, System.currentTimeMillis()), () -> {});

        Task t2 = new Task("task", 6,1, System.currentTimeMillis());
        data.task().insertTask(t2, () -> {});
        taskSorter.getTasksByPriority(tasks -> {
            assertEquals("Task Sorter is improperly getting completed tasks by priority!",
                    tasks.iterator().next(), t2);
        });
    }

    /**
     * Tests that sorting tasks by completion functions as expected (higher priority first)
     */
    @Test
    public void testGetByCreation() throws InterruptedException {
        Task t1 = new Task("task", 5, 5, System.currentTimeMillis());
        //make t1 created 10ms before the second task.
        Thread.sleep(10);
        data.task().insertTask(new Task("task2", 5, 5,System.currentTimeMillis()), () -> {});
        data.task().insertTask(t1, () -> {});
        taskSorter.getTasksByCreation(tasks -> {
            assertEquals("Task sorter is improperly getting completed tasks by creation!",
                    tasks.iterator().next(), t1);
        });
    }


    /**
     * Tests that sorting tasks by due date functions as expected (closest due first)
     */
    @Test
    public void testGetByDueDate() throws InterruptedException {
        Task t1 = new Task("task", 5, 5, System.currentTimeMillis(), LocalDate.now(), null);
        data.task().insertTask(new Task("task2", 5, 5,System.currentTimeMillis(), LocalDate.now().plusDays(1), null), () -> {});
        data.task().insertTask(t1, () -> {});
        data.task().insertTask(new Task("task3", 5, 5,System.currentTimeMillis(), LocalDate.now().plusDays(2), null), () -> {});
        taskSorter.getTasksByDueDate(tasks -> {
            assertEquals("Task sorter is improperly getting completed tasks by due date!",
                    tasks.iterator().next(), t1);
        });
    }

    /**
     * Tests that the get by date function works, getting tasks by due date
     */
    @Test
    public void testDateFilterPositive(){
        Task t1 = new Task("task", 5, 5, System.currentTimeMillis(), LocalDate.now(), null);
        //make t1 created 10ms before the second task.
        data.task().insertTask(t1, () ->{});
        taskSorter.getTasksOnDate(LocalDate.now(),tasks -> {
            assertFalse("Task Sorter filter by date accidently filtered an actual result :(", tasks.isEmpty());
        });
    }

    /**
     * Tests that the get by date function works, filtering out tasks not on the requested due date
     */
    @Test
    public void testDateFilterNegative(){
        Task t1 = new Task("task", 5, 5, System.currentTimeMillis(), LocalDate.now().plusDays(1), null);
        //make t1 created 10ms before the second task.
        data.task().insertTask(t1, () ->{});
        taskSorter.getTasksOnDate(LocalDate.now(),tasks -> {
            assertTrue("Task Sorter filter by date accidently included a result that should've been filtered.", tasks.isEmpty());
        });
    }

    /**
     * Tests that TaskManager.getCompletedTasks includes completed tasks
     */
    @Test
    public void testGetCompletedIncludes(){
        Task t1 = new Task("task", 5);

        data.task().insertTask(t1, () ->{});
        t1.setCompleted(LocalDateTime.now());
        taskSorter.getCompletedTasks(tasks -> {
            assertTrue("TaskManager Get Completed tasks missed a completed task.", tasks.contains(t1));
        });
    }

    /**
     * Tests that TaskManager.getCompletedTasks excludes incompleted tasks
     */
    @Test
    public void testGetCompletedExcludes(){
        Task t2 = new Task("task2", 5, 1, System.currentTimeMillis());
        data.task().insertTask(t2, () ->{});
        taskSorter.getCompletedTasks(tasks -> {
            assertFalse("TaskManager Get Completed tasks didn't included an incomplete task.", tasks.contains(t2));
        });
    }






}
