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
import com.productive6.productive.logic.rewards.impl.RewardManager;
import com.productive6.productive.system.AddTaskTest;
import com.productive6.productive.system.BuyCosmeticTest;
import com.productive6.productive.system.CalanderTest;
import com.productive6.productive.system.CategorizeTaskTest;
import com.productive6.productive.system.EditTaskTest;
import com.productive6.productive.system.NotificationTest;
import com.productive6.productive.system.ProductivityTest;
import com.productive6.productive.system.RewardTest;
import com.productive6.productive.system.SortingTest;
import com.productive6.productive.system.StreakTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all associated system tests in this project.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AddTaskTest.class,
        BuyCosmeticTest.class,
        CalanderTest.class,
        CategorizeTaskTest.class,
        EditTaskTest.class,
        NotificationTest.class,
        ProductivityTest.class,
        RewardTest.class,
        SortingTest.class,
        StreakTest.class
})
@LargeTest
public class AllSystem {
}
