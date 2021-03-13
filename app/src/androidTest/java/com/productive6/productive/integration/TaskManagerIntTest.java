package com.productive6.productive.integration;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.exceptions.ObjectFormatException;
import com.productive6.productive.logic.exceptions.PersistentIDAssignmentException;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.logic.task.impl.PersistentTaskManager;
import com.productive6.productive.logic.task.impl.PersistentTaskSorter;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.task.TaskCreateEvent;
import com.productive6.productive.objects.events.task.TaskUpdateEvent;
import com.productive6.productive.persistence.datamanage.impl.InMemoryAndroidDataManager;
import com.productive6.productive.persistence.executor.IRunnableExecutor;
import com.productive6.productive.persistence.executor.impl.TestExecutor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests logic-layer task manager verification and computation
 */
@RunWith(AndroidJUnit4.class)
public class TaskManagerIntTest {

    private Context mContext;

    private IRunnableExecutor mRunnableExecutor;

    private ITaskManager taskManager;

    //for accessing tasks in the one task date test
    private ITaskSorter taskSorter;

    @Before
    public void init(){
        mContext = InstrumentationRegistry.getInstrumentation().getContext();
        mRunnableExecutor = new TestExecutor();
        InMemoryAndroidDataManager data = new InMemoryAndroidDataManager(mContext, mRunnableExecutor);
        data.init();
        taskManager = new PersistentTaskManager(data);
        taskSorter = new PersistentTaskSorter(data);
    }

    /**
     * Tests that insertion id checking is functional
     * when it needs to be
     */
    @Test(expected = PersistentIDAssignmentException.class )
    public void testIDFormatInsert() {
        Task testData = new Task("name", 1, 1, System.currentTimeMillis(), LocalDate.now(), null);
        testData.setId(1);
        taskManager.addTask(testData);
    }

    /**
     * Tests that insertion/retrieval of dates within tasks works as expected.
     * (White box test)
     */
    @Test
    public void testDateConversion() {
        LocalDate day = LocalDate.now().minusDays(25);
        Task testData = new Task("name", 1, 1, System.currentTimeMillis(), day, null);
        taskManager.addTask(testData);

        taskSorter.getTasksByPriority(tasks -> {
            assertEquals(tasks.get(0).getDueDate(), LocalDate.now());
        });
    }

    /**
     * Tests that update id checking is functional
     * when it needs tobe
     */
    @Test(expected = PersistentIDAssignmentException.class)
    public void testIDFormatUpdate() {
        Task testData = new Task("name", 1, 1, System.currentTimeMillis(), LocalDate.now(), null);
        testData.setId(0);
        taskManager.updateTask(testData);
    }

    /**
     * Tests that insertion completion checking is functional
     */
    @Test(expected = ObjectFormatException.class)
    public void testCompletionChecking(){
        Task testData = new Task("name", 1, 1, System.currentTimeMillis(), LocalDate.now(), null);
        taskManager.addTask(testData);
    }

    /**
     * Tests that insertion priorty checking is functional
     */
    @Test(expected = ObjectFormatException.class)
    public void testPriorityChecking(){
        Task testData = new Task("name", -1, 1, System.currentTimeMillis(), LocalDate.now(), null);
        taskManager.addTask(testData);
    }

    /**
     * Tests that the task manager has the ability to autofill times
     */
    @Test
    public void testTimeAutofill(){
        Task testData = new Task("name", 1, 1, 0, LocalDate.now(), null);
        taskManager.addTask(testData);
        assertTrue("Task Manager didn't autofill time correctly.", Math.abs(System.currentTimeMillis()-testData.getCreatedTime()) < 10000);
    }

    @Test
    public void testCompleteTask(){
        Task testData = new Task("name", 1, 1, 0, LocalDate.now(), null);
        taskManager.addTask(testData);

        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(TaskCreateEvent e){
                taskManager.completeTask(testData);
                assertTrue("Task Manager didn't autofill completion correctly.", testData.isCompleted());
            }
        });
    }

    @Test
    public void testUpdateEvent(){
        AtomicBoolean success = new AtomicBoolean(false);
        Task testData = new Task("name", 1, 1, 0, LocalDate.now(), null);
        taskManager.addTask(testData);

        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(TaskCreateEvent e){
                taskManager.updateTask(testData);
            }
        });
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(TaskUpdateEvent e){
                success.set(true);
                assertTrue("TaskManager failed to trigger a user updated event when necessary.",success.get());
            }
        });
    }

    @Test
    public void testAddEvent(){
        AtomicBoolean success = new AtomicBoolean(false);
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(TaskCreateEvent e){
                success.set(true);
                assertTrue("TaskManager failed to trigger a user create event when necessary.",success.get());
            }
        });
        Task testData = new Task("name", 1, 1, 0, LocalDate.now(), null);
        taskManager.addTask(testData);
    }





}
