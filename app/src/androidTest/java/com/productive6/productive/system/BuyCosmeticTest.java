package com.productive6.productive.system;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.productive6.productive.R;
import com.productive6.productive.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.productive6.productive.system.utils.Utils.atPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
@HiltAndroidTest
public class BuyCosmeticTest {

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Performs buy cosmetics in Shop and check whether item exists in Inventory after having bought
     */
    @Test
    public void buyCosmeticTest() {
        //add task and complete to have coins
        //navigate to To-do fragment
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_todo), withContentDescription("To-do"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        //click add task button
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.newTaskButton), withText("Add Task"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                7),
                        isDisplayed()));
        materialButton.perform(click());

        //perform actions in the popup window
        //in order to use the popup, you need to add the 'inroot isplatformpopup' part
        onView(withId(R.id.taskNameForm)).inRoot(RootMatchers.isPlatformPopup())
                //type text and close keyboard
                .perform(typeText("test"), closeSoftKeyboard());

        //submit task
        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.submit), withText("Submit"),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        //complete task
        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.taskCompleteToggleButton),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        childAtPosition(
                                                withId(R.id.taskDisplayView),
                                                0)),
                                6),
                        isDisplayed()));
        materialButton3.perform(click());

        //navigate to rewards fragment
        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_rewards), withContentDescription("Rewards"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        //go to Shop
        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.shopButton), withText("Shop"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                9),
                        isDisplayed()));
        materialButton4.perform(click());

        //Buy the item named Armor 1
        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.buyButton), withText("Buy"),
                        childAtPosition(
                                allOf(childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),hasDescendant(allOf(withId(R.id.propName),withText("Armor 1")))),
                                3),
                        isDisplayed()));
        materialButton5.perform(click());

        //Confirm buying
        ViewInteraction materialButton6 = onView(
                allOf(withId(android.R.id.button1), withText("Confirm"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton6.perform(scrollTo(), click());

        //Return to main activity
        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.returnButtonShop), withText("RETURN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton7.perform(click());

        //Go to Inventory
        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.inventoryButton), withText("INVENTORY"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()));
        materialButton8.perform(click());

        //Assert if item named Armor 1 appeared in Inventory
        onView(withId(R.id.inventoryDisplayView))
                .check(matches(atPosition(0,
                        hasDescendant(allOf(withText("Armor 1"), withId(R.id.item_name)))
                )));

        //Return to main activity and end test
        ViewInteraction materialButton9 = onView(
                allOf(withId(R.id.backButton), withText("RETURN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton9.perform(click());
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
