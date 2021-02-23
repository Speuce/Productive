package com.productive6.productive.logic.executor;

/**
 * Handles running method sync/async
 */
public interface IRunnableExecutor {

    /**
     * Runs the given runnable asyncrhonously/
     * not on the main thread
     * @param b the runnable to run.
     */
    void runASync(Runnable b);

    /**
     * Runs the given runnable on the main thread
     * @param b the runnable to run.
     */
    void runSync(Runnable b);
}
