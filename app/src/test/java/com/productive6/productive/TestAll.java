package com.productive6.productive;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all associated unit tests in this project.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({TaskManagerTest.class, ITitleManagerTest.class, EventDispatchTest.class}, RewardManagerTest.class)
public class TestAll {
}
