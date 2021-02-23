package com.productive6.productive.logic.executor.impl;

import com.productive6.productive.logic.executor.IRunnableExecutor;

/**
 * Implementation of {@link IRunnableExecutor} for unit tests.
 * All runnables are ran on the main thread.
 */
public class TestExecutor implements IRunnableExecutor {

    @Override
    public void runASync(Runnable b) {
        b.run();
    }

    @Override
    public void runSync(Runnable b) {
        b.run();
    }
}
