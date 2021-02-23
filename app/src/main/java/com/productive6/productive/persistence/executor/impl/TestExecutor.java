package com.productive6.productive.persistence.executor.impl;

import com.productive6.productive.persistence.executor.IRunnableExecutor;

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
