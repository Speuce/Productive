package com.productive6.productive.system;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.productive6.productive.R;
import com.productive6.productive.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
@HiltAndroidTest
public class CategorizeTaskTest {

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    String selectionText;

    @Before
    public void init(){
        selectionText = "School";
    }


    @Test
    public void categorizeTaskTest(){

        onView(withId(R.id.navigation_todo)).perform(click());
        onView(withId(R.id.newTaskButton)).perform(click());

        onView(withId(R.id.categorySpinner)).perform(click());
        onData(allOf(is(selectionText))).perform(click());

        onView(withId(R.id.submit)).perform(click());

        onView(withId(R.id.taskCategoryTextView)).check(matches(withText(selectionText)));

    }



}
