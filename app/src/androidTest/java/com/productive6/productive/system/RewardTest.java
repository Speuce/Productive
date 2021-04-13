package com.productive6.productive.system;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import com.productive6.productive.R;
import com.productive6.productive.logic.rewards.IStreakRewardManager;
import com.productive6.productive.ui.MainActivity;

import javax.inject.Inject;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
@HiltAndroidTest
public class RewardTest {

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void basicRewardsTask() {

        onView(withId(R.id.navigation_todo)).perform(click());
        onView(withId(R.id.newTaskButton)).perform(click());
        onView(withId(R.id.submit)).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.taskCompleteToggleButton)).perform(click());
        onView(withId(R.id.navigation_rewards)).perform(click());

        onView(withId(R.id.coin_counter)).check(matches(withText("7")));

    }

}
