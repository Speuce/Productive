package com.productive6.productive.system;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.productive6.productive.R;
import com.productive6.productive.ui.MainActivity;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.productive6.productive.system.utils.Utils.atPosition;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
@LargeTest
@HiltAndroidTest
public class AddTaskTest {

    private String stringToBetyped;

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void init() {
        stringToBetyped = "Espresso";
    }

    /**
     * Performs a basic 'add task' and asserts that the task name shown is the task name provided.
     */
    @Test
    public void basicAddTask() {
        //open the 'add task popup'
        //withId filters to find components in the current view with the given id
        //you can also filter by component title/text or whatever. but for us id is fine.
        onView(withId(R.id.navigation_todo)).perform(click());
        onView(withId(R.id.newTaskButton)).perform(click());

        //perform actions in the popup window
        //in order to use the popup, you need to add the 'inroot isplatformpopup' part
        onView(withId(R.id.taskNameForm)).inRoot(RootMatchers.isPlatformPopup())
                //multiple actions can be performed, in this case, first is typeText, next is close keyboard
                .perform(typeText(stringToBetyped), closeSoftKeyboard());
        onView(withId(R.id.submit)).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        //okay now for assertion(s)
        //since the tasks are listed in an separate view (the recycler view) we ha
        //we actually have to
        //gonna be completely honest this is from stackoverflow
        //dont entirely know how it works, but i have a basic idea..
        //https://stackoverflow.com/a/34795431/6047183
        onView(withId(R.id.taskDisplayView))
                .check(matches(atPosition(0,
                        //this next part i understand
                        //since each entry in the recyclerView is itself its own view,
                        //you have to search in the DESCENDANTS of the view at position 0
                        //allOf is just set intersection (so you want ALL of these conditions to
                        //be valid.
                        hasDescendant(allOf(withText(stringToBetyped), withId(R.id.taskNameTextView)))
                       )));
    }

}
