package com.productive6.productive;

import com.productive6.productive.logic.exceptions.PersistentIDAssignmentException;
import com.productive6.productive.logic.exceptions.TaskFormatException;
import com.productive6.productive.logic.task.TaskManager;
import com.productive6.productive.logic.task.impl.PersistentTaskManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.persistence.dummy.DummyDataManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests logic-layer task manager verification and computation
 */
public class TaskManagerTest {

    private TaskManager taskManager;

    @Before
    public void init(){
        taskManager = new PersistentTaskManager(new DummyDataManager());
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
                "Task Manager didn't catch an id exception",
                PersistentIDAssignmentException.class,
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
                TaskFormatException.class,
                () -> taskManager.addTask(testData));
    }

    /**
     * Tests that insertion priorty checking is functional
     */
    @Test
    public void testPriorityChecking(){
        Task testData = new Task("name", -1, System.currentTimeMillis(), false);
        assertThrows("Task Manager missed a negative priority",
                TaskFormatException.class,
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




}
