package com.productive6.productive;

import com.productive6.productive.logic.rewards.impl.RewardSpenderManager;
import com.productive6.productive.logic.statstics.impl.StatsManager;
import com.productive6.productive.unit.CosmeticAdapterTest;
import com.productive6.productive.unit.CosmeticManagerTest;
import com.productive6.productive.unit.EventDispatchTest;
import com.productive6.productive.unit.RewardManagerTest;
import com.productive6.productive.unit.RewardSpenderManagerTest;
import com.productive6.productive.unit.StatsManagerTest;
import com.productive6.productive.unit.StreakManagerTest;
import com.productive6.productive.unit.TaskManagerTest;
import com.productive6.productive.unit.TaskSorterTest;
import com.productive6.productive.unit.TitleManagerTest;
import com.productive6.productive.unit.UserManagerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * Runs all associated unit tests in this project.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        RewardSpenderManagerTest.class,
        TaskManagerTest.class,
        TitleManagerTest.class,
        EventDispatchTest.class,
        UserManagerTest.class,
        RewardManagerTest.class,
        TaskSorterTest.class,
        StatsManagerTest.class,
        StreakManagerTest.class,
        CosmeticManagerTest.class,
        CosmeticAdapterTest.class
})

public class TestAll {
}
