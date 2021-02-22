package com.productive6.productive.logic.executor.impl;

import android.os.Handler;
import android.os.Looper;

import com.productive6.productive.logic.executor.IRunnableExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Specfic implementation of {@link IRunnableExecutor} for
 * android applications
 */
public class AndroidExecutor implements IRunnableExecutor {

    /**
     * Handles running async processes
     */
    private final ExecutorService executorService;

    /**
     * Handles synchronous processes
     */
    private final Handler syncHandler;

    public AndroidExecutor() {
        executorService = Executors.newFixedThreadPool(2);
        syncHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void runASync(Runnable b) {
        executorService.execute(b);
    }

    @Override
    public void runSync(Runnable b) {
        syncHandler.post(b);
    }
}
