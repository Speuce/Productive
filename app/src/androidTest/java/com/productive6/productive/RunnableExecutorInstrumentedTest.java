package com.productive6.productive;

import android.os.Looper;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.productive6.productive.persistence.executor.IRunnableExecutor;
import com.productive6.productive.persistence.executor.impl.AndroidExecutor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


/**
 * Test for the {@link com.productive6.productive.persistence.executor.IRunnableExecutor} interface.
 *
 * Ensures that the used implementation correctly runs tasks on/off the main thread.
 */
@RunWith(AndroidJUnit4.class)
public class RunnableExecutorInstrumentedTest {

    private IRunnableExecutor executor;

    @Before
    public void init(){
        executor = new AndroidExecutor();
    }

    @Test
    public void testMainIsMain(){
        executor.runSync(() ->{
            assertSame("Tested Runnable Executor is not running sync tasks on the main thread!",
                    Thread.currentThread(), Looper.getMainLooper().getThread());
        });
    }

    @Test
    public void testASyncIsNotMain(){
        executor.runASync(() ->{
            assertNotSame("Tested Runnable Executor is running ASync tasks on the main thread!",
                    Thread.currentThread(), Looper.getMainLooper().getThread());
        });
    }
}