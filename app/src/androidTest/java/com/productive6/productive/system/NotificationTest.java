package com.productive6.productive.system;

import android.util.Log;

import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import com.productive6.productive.R;
import com.productive6.productive.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.productive6.productive.system.utils.Utils.atPosition;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
@LargeTest
@HiltAndroidTest
public class NotificationTest {

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    private UiDevice device;

    @Before
    public void init() {
        //wait for notification
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    /**
     * Performs a basic 'add task', with a date deadline,
     * exits the app, and asserts that a notification occurs
     */
    @Test
    public void testNotification() throws InterruptedException {

        //open the 'add task popup'
        //withId filters to find components in the current view with the given id
        //you can also filter by component title/text or whatever. but for us id is fine.
        onView(withId(R.id.navigation_todo)).perform(click());
        onView(withId(R.id.newTaskButton)).perform(click());

        //perform actions in the popup window
        //in order to use the popup, you need to add the 'inroot isplatformpopup' part
        onView(withId(R.id.taskNameForm)).inRoot(RootMatchers.isPlatformPopup())
                //multiple actions can be performed, in this case, first is typeText, next is close keyboard
                .perform(typeText("test"), closeSoftKeyboard());
        //activate deadline
        onView(withId(R.id.switchDeadline)).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        //submit
        onView(withId(R.id.submit)).inRoot(RootMatchers.isPlatformPopup()).perform(click());



        //exit the app
        device.pressHome();

        String title = "Productive";

        device.openNotification();

        device.wait(Until.hasObject(By.text(title)), 30000);
        UiObject2 titleobj = device.findObject(By.text(title));
        assertEquals(title, titleobj.getText());

    }

    @After
    public void cleanup(){
        device.pressHome();
    }



}
