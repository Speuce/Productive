package com.productive6.productive;

import androidx.test.filters.LargeTest;

import com.productive6.productive.integration.RewardManagerIntTest;
import com.productive6.productive.integration.RunnableExecutorInstrumentedTest;
import com.productive6.productive.integration.StatsManagerIntTest;
import com.productive6.productive.integration.StreakRewardManagerIntTest;
import com.productive6.productive.integration.TaskManagerIntTest;
import com.productive6.productive.integration.TaskSorterIntTest;
import com.productive6.productive.integration.TitleManagerIntTest;
import com.productive6.productive.integration.UserManagerIntTest;
import com.productive6.productive.system.AddTaskTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all associated unit tests in this project.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AddTaskTest.class
})
@LargeTest
public class AllSystem {


}
