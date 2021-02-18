package com.productive6.productive.executor;

/**
 * Implementation of {@link RunnableExecutor} for unit tests.
 * All runnables are ran on the main thread.
 */
public class TestExecutor implements RunnableExecutor {

    @Override
    public void runASync(Runnable b) {
        b.run();
    }

    @Override
    public void runSync(Runnable b) {
        b.run();
    }
}
