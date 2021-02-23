package com.productive6.productive;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.exceptions.PersistentIDAssignmentException;
import com.productive6.productive.logic.exceptions.ObjectFormatException;
import com.productive6.productive.logic.task.TaskManager;
import com.productive6.productive.logic.task.impl.PersistentTaskManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.task.TaskCreateEvent;
import com.productive6.productive.objects.events.task.TaskUpdateEvent;
import com.productive6.productive.objects.events.user.UserUpdateEvent;
import com.productive6.productive.persistence.dummy.DummyDataManager;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

/**
 * Tests logic-layer task manager verification and computation
 */
public class TaskManagerTest {

    private TaskManager taskManager;

    private DummyDataManager data;

    @Before
    public void init(){
        data = new DummyDataManager();
        taskManager = new PersistentTaskManager(data);
    }


    /**
     * Tests that sorting tasks by priority functions as expected (higher priority first)
     */
    @Test
    public void testGetByPriority(){
        taskManager.addTask(new Task("task", 5, System.currentTimeMillis()));

        Task t2 = new Task("task", 6, System.currentTimeMillis());
        taskManager.addTask(t2);
        taskManager.getTasksByPriority(tasks -> {
            assertEquals("Task Manager is improperly getting completed tasks by priority!",
                    tasks.iterator().next(), t2);
        });
    }

    /**
     * Tests that sorting tasks by completion functions as expected (higher priority first)
     */
    @Test
    public void testGetByCreation() throws InterruptedException {
        Task t1 = new Task("task", 5, System.currentTimeMillis(), 0);
        //make t1 created 10ms before the second task.
        Thread.sleep(10);
        taskManager.addTask(new Task("task2", 5, System.currentTimeMillis(), 0));
        taskManager.addTask(t1);
        taskManager.getTasksByCreation(tasks -> {
            assertEquals("Task Manager is improperly getting completed tasks by creation!",
                    tasks.iterator().next(), t1);
        });
    }

    /**
     * Tests that TaskManager.getCompletedTasks includes completed tasks
     */
    @Test
    public void testGetCompletedIncludes(){
        Task t1 = new Task("task", 5);
        taskManager.addTask(t1);
        t1.setCompleted(true);
        taskManager.getCompletedTasks(tasks -> {
            assertTrue("TaskManager Get Completed tasks missed a completed task.", tasks.contains(t1));
        });
    }

    /**
     * Tests that TaskManager.getCompletedTasks excludes incompleted tasks
     */
    @Test
    public void testGetCompletedExcludes(){
        Task t2 = new Task("task2", 5, System.currentTimeMillis());
        taskManager.addTask(t2);
        taskManager.getCompletedTasks(tasks -> {
            assertFalse("TaskManager Get Completed tasks didn't included an incomplete task.", tasks.contains(t2));
        });
    }

    /**
     * Tests that insertion id checking is functional
     * when it needs tobe
     */
    @Test
    public void testIDFormatInsert() {
        Task testData = new Task("name", 1, System.currentTimeMillis(), false);
        testData.setId(1);
        assertThrows(
                "Task Manager didn't catch an id exception on insert.",
                PersistentIDAssignmentException.class,
                () -> taskManager.addTask(testData)

        );
    }

    /**
     * Tests that update id checking is functional
     * when it needs tobe
     */
    @Test
    public void testIDFormatUpdate() {
        Task testData = new Task("name", 1, System.currentTimeMillis(), false);
        testData.setId(0);
        assertThrows(
                "Task Manager didn't catch an id exception on update.",
                PersistentIDAssignmentException.class,
                () -> taskManager.updateTask(testData)

        );
    }

    @Test
    public void testDueTimeValidation() {
        Task testData = new Task("name", 1, System.currentTimeMillis(), 100);
        assertThrows(
                "Task Manager properly validate the due time of a task!",
                    TaskFormatException.class,
                () -> taskManager.addTask(testData)
        );
    }

    /**
     * Tests that insertion completion checking is functional
     */
    @Test
    public void testCompletionChecking(){
        Task testData = new Task("name", 1, System.currentTimeMillis(), true);
        assertThrows("Task Manager missed a 'completed' flag = true",
                ObjectFormatException.class,
                () -> taskManager.addTask(testData));
    }

    /**
     * Tests that insertion priorty checking is functional
     */
    @Test
    public void testPriorityChecking(){
        Task testData = new Task("name", -1, System.currentTimeMillis(), false);
        assertThrows("Task Manager missed a negative priority",
                ObjectFormatException.class,
                () -> taskManager.addTask(testData));
    }

    /**
     * Tests that the task manager has the ability to autofill times
     */
    @Test
    public void testTimeAutofill(){
        Task testData = new Task("name", 1, 0, false);
        taskManager.addTask(testData);
        assertTrue("Task Manager didn't autofill time correctly.", Math.abs(System.currentTimeMillis()-testData.getCreatedTime()) < 10000);
    }

    @Test
    public void testCompleteTask(){
        Task testData = new Task("name", 1, 0, false);
        taskManager.addTask(testData);
        taskManager.completeTask(testData);
        assertTrue("Task Manager didn't autofill completion correctly.", testData.isCompleted());
    }

    @Test
    public void testUpdateEvent(){
        AtomicBoolean success = new AtomicBoolean(false);
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(TaskUpdateEvent e){
                success.set(true);
            }
        });
        Task testData = new Task("name", 1, 0, false);
        taskManager.addTask(testData);
        taskManager.updateTask(testData);
        assertTrue("TaskManager failed to trigger a user updated event when necessary.",success.get());
    }

    @Test
    public void testAddEvent(){
        AtomicBoolean success = new AtomicBoolean(false);
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(TaskCreateEvent e){
                success.set(true);
            }
        });
        Task testData = new Task("name", 1, 0, false);
        taskManager.addTask(testData);
        assertTrue("TaskManager failed to trigger a user create event when necessary.",success.get());
    }





}
