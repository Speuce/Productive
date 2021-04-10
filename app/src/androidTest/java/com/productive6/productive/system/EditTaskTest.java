package com.productive6.productive.system;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.productive6.productive.R;
import com.productive6.productive.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.productive6.productive.system.utils.Utils.atPosition;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
@HiltAndroidTest
public class EditTaskTest {
    private String stringToBetyped;

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void init() {
        stringToBetyped = "Edited";
    }

    /**
     * Performs a basic 'edit task' and asserts that the task name shown is the task name after having edited.
     */
    @Test
    public void basicEditTask() {
        //navigate to To-do fragment
        onView(withId(R.id.navigation_todo)).perform(click());
        //click new task button
        onView(withId(R.id.newTaskButton)).perform(click());

        //perform actions in the popup window
        //in order to use the popup, you need to add the 'inroot isplatformpopup' part
        onView(withId(R.id.taskNameForm)).inRoot(RootMatchers.isPlatformPopup())
                //type before as task name
                .perform(typeText("Before"), closeSoftKeyboard());
        //submit task
        onView(withId(R.id.submit)).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        //click edit button
        onView(allOf(withId(R.id.editButton),childAtPosition(
                allOf(
                    withId(R.id.constraintLayout), childAtPosition(
                            withId(R.id.taskDisplayView),
                            0)),
                    5),
                isDisplayed())).perform(click());

        //replace text
        onView(withId(R.id.taskNameForm)).inRoot(RootMatchers.isPlatformPopup())
                .perform(replaceText(stringToBetyped), closeSoftKeyboard());
        //submit edit
        onView(withId(R.id.submit)).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        //assert name after having edited
        onView(withId(R.id.taskDisplayView))
                .check(matches(atPosition(0,
                        hasDescendant(allOf(withText(stringToBetyped), withId(R.id.taskNameTextView)))
                )));
    }

    /**
     * auto generated from Espresso Test Recorder
     * as I understand, this method is to get view from view of parent and its position
     * useful to get view from recycler view
     */
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}
