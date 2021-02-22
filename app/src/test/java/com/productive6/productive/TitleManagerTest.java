package com.productive6.productive;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.ITitleManager;
import com.productive6.productive.logic.rewards.impl.DefaultTitleManager;
import com.productive6.productive.logic.user.UserManager;
import com.productive6.productive.objects.Title;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.objects.events.user.UserUpdateEvent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static org.junit.Assert.assertTrue;


public class TitleManagerTest {


    @Mock
    UserManager data;

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    ITitleManager TitleManager;

    @Before
    public void init(){
        String[] tempTitles = {"TEST1", "TEST2", "TEST3"};
        int[] tempVals = {1, 2, 3};
        TitleManager = new DefaultTitleManager(data,tempTitles,tempVals);

    }

    @Test
    public void testAllTitleStrings(){

        EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0, 15, 0)));
        List<Title> testList = TitleManager.getTitleOptions();

        assertTrue("First element string is equal", testList.get(0).getString().equals("TEST1"));
        assertTrue("First element string is equal", testList.get(1).getString().equals("TEST2"));
        assertTrue("First element string is equal", testList.get(2).getString().equals("TEST3"));

    }

    @Test
    public void testAllTitleLevels(){

        EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0, 15, 0)));
        List<Title> testList = TitleManager.getTitleOptions();

        assertTrue("First element string is equal", testList.get(0).getLevelRequirement() == 1);
        assertTrue("First element string is equal", testList.get(1).getLevelRequirement() == 2);
        assertTrue("First element string is equal", testList.get(2).getLevelRequirement() == 3);

    }

    @Test
    public void testValidTitle(){

        EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0, 10, 0)));

        TitleManager.setTitle("TEST2");

        assertTrue("The title has not been successfully set to TEST2", TitleManager.getTitleAsString().equals("TEST2"));

    }

    @Test
    public void testInvalidTitle(){

        EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0, 10, 0)));
        TitleManager.setTitle("TEST2");
        assertTrue("The title was not successfully set to TEST2", TitleManager.getTitleAsString().equals("TEST2"));

        TitleManager.setTitle("INVALID");
        assertTrue("The title was changed", TitleManager.getTitleAsString().equals("TEST2"));

    }

    @Test
    public void testOptions(){

            EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0, 2, 0)));

            List<Title> testList = TitleManager.getTitleOptions();

            assertTrue("First element string is not equal", testList.get(0).getString().equals("TEST1"));
            assertTrue("Second element string is not equal", testList.get(1).getString().equals("TEST2"));
            assertTrue("List is not of correct length", testList.size() == 2);
    }


    @Test
    public void testEventUpdating() {

        EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0, 1, 0)));
        assertTrue("Expected to have only 1 title option", TitleManager.getTitleOptions().size() == 1 );

        EventDispatch.dispatchEvent((new UserUpdateEvent(new User(0,3,0))));

        assertTrue("Expected to have 3 title options", TitleManager.getTitleOptions().size() == 3 );

    }
}
