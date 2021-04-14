package com.productive6.productive.unit;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.exceptions.PersistentIDAssignmentException;
import com.productive6.productive.logic.exceptions.ObjectFormatException;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.logic.task.impl.PersistentTaskManager;
import com.productive6.productive.logic.task.impl.PersistentTaskSorter;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.enums.Difficulty;
import com.productive6.productive.objects.enums.Priority;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.task.TaskCreateEvent;
import com.productive6.productive.objects.events.task.TaskUpdateEvent;
import com.productive6.productive.persistence.datamanage.IDataManager;
import com.productive6.productive.persistence.datamanage.dummy.DummyDataManager;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

/**
 * Tests logic-layer task manager verification and computation
 */
public class TaskManagerTest {

    private ITaskManager taskManager;

    @Before
    public void init(){
        IDataManager data = new DummyDataManager();
        int config[] = {3,3};
        taskManager = new PersistentTaskManager(data,config);
    }


    /**
     * Tests that insertion id checking is functional
     * when it needs tobe
     */
    @Test
    public void testIDFormatInsert() {
        Task testData = new Task("name", Priority.LOW, Difficulty.EASY, LocalDateTime.now(), LocalDate.now(), null);
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
        Task testData = new Task("name", Priority.LOW, Difficulty.EASY, LocalDateTime.now(), LocalDate.now(), null);
        testData.setId(0);
        assertThrows(
                "Task Manager didn't catch an id exception on update.",
                PersistentIDAssignmentException.class,
                () -> taskManager.updateTask(testData)

        );
    }


    /**
     * Tests that insertion completion checking is functional
     */
    @Test
    public void testCompletionChecking(){
        Task testData = new Task("name", Priority.LOW, Difficulty.EASY, LocalDateTime.now(), LocalDate.now(), LocalDateTime.now());
        assertThrows("Task Manager missed a 'completed' flag = true",
                ObjectFormatException.class,
                () -> taskManager.addTask(testData));
    }


    @Test
    public void testCompleteTask(){
        Task testData = new Task("name", Priority.LOW, Difficulty.EASY, LocalDateTime.now(), LocalDate.now(), null);
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
        Task testData = new Task("name", Priority.LOW, Difficulty.EASY, LocalDateTime.now(), LocalDate.now(), null);
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
        Task testData = new Task("name", Priority.LOW, Difficulty.EASY, LocalDateTime.now(), LocalDate.now(), null);
        taskManager.addTask(testData);
        assertTrue("TaskManager failed to trigger a user create event when necessary.",success.get());
    }





}
