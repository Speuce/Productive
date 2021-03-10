package com.productive6.productive;

import android.os.UserManager;

import com.productive6.productive.integration.RewardManagerIntTest;
import com.productive6.productive.integration.TaskManagerIntTest;
import com.productive6.productive.integration.TitleManagerIntTest;
import com.productive6.productive.unit.EventDispatchTest;
import com.productive6.productive.unit.RewardManagerTest;
import com.productive6.productive.integration.UserManagerIntTest;
import com.productive6.productive.unit.TaskManagerTest;
import com.productive6.productive.unit.TitleManagerTest;
import com.productive6.productive.unit.UserManagerTest;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * Runs all associated unit tests in this project.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({TaskManagerTest.class, TitleManagerTest.class, EventDispatchTest.class, UserManagerTest.class, RewardManagerTest.class, UserManagerIntTest.class, TaskManagerIntTest.class, RewardManagerIntTest.class, TitleManagerIntTest.class, TaskSorterTest.class})
public class TestAll {
}
