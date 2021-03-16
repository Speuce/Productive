package com.productive6.productive;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.productive6.productive.integration.RewardManagerIntTest;
import com.productive6.productive.integration.RunnableExecutorInstrumentedTest;
import com.productive6.productive.integration.StatsManagerIntTest;
import com.productive6.productive.integration.TaskManagerIntTest;
import com.productive6.productive.integration.TaskSorterIntTest;
import com.productive6.productive.integration.TitleManagerIntTest;
import com.productive6.productive.integration.UserManagerIntTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all associated unit tests in this project.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        RewardManagerIntTest.class,
        RunnableExecutorInstrumentedTest.class,
        TaskManagerIntTest.class,
        TaskSorterIntTest.class,
        TitleManagerIntTest.class,
        UserManagerIntTest.class,
        StatsManagerIntTest.class
})
public class TestAllIntegration {


}
