package com.productive6.productive;

import android.content.res.Resources;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.TitleManager;
import com.productive6.productive.logic.rewards.impl.DefaultTitleManager;
import com.productive6.productive.logic.user.UserManager;
import com.productive6.productive.objects.Title;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.objects.events.user.UserUpdateEvent;
import com.productive6.productive.persistence.datamanage.DataManager;
import com.productive6.productive.persistence.dummy.DummyTitleDataManager;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;



public class TitleManagerTest {


    @Mock
    Resources res;

    @Mock
    UserManager data;


    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void testAllTitleStrings(){
        String[] tempTitles = {"TEST1", "TEST2","TEST3"};
        int[] tempVals = {1,2,3};

        when(res.getStringArray(R.array.TitleStringArray)).thenReturn(tempTitles);
        when(res.getIntArray(R.array.TitleLevelArray)).thenReturn(tempVals);


        TitleManager titleManager = new DefaultTitleManager(data,res);
        EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0, 15, 0)));
        List<Title> testList = titleManager.getTitleOptions();

        assertTrue("First element string is equal", testList.get(0).getString().equals("TEST1"));
        assertTrue("First element string is equal", testList.get(1).getString().equals("TEST2"));
        assertTrue("First element string is equal", testList.get(2).getString().equals("TEST3"));

    }

    @Test
    public void testAllTitleLevels(){
        String[] tempTitles = {"TEST1", "TEST2","TEST3"};
        int[] tempVals = {1,2,3};

        when(res.getStringArray(R.array.TitleStringArray)).thenReturn(tempTitles);
        when(res.getIntArray(R.array.TitleLevelArray)).thenReturn(tempVals);

        TitleManager titleManager = new DefaultTitleManager(data,res);
        EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0, 15, 0)));
        List<Title> testList = titleManager.getTitleOptions();

        assertTrue("First element string is equal", testList.get(0).getLevelRequirement() == 1);
        assertTrue("First element string is equal", testList.get(1).getLevelRequirement() == 2);
        assertTrue("First element string is equal", testList.get(2).getLevelRequirement() == 3);

    }

    @Test
    public void testValidTitle(){
        String[] tempTitles = {"TEST1", "TEST2","TEST3"};
        int[] tempVals = {1,2,3};

        when(res.getStringArray(R.array.TitleStringArray)).thenReturn(tempTitles);
        when(res.getIntArray(R.array.TitleLevelArray)).thenReturn(tempVals);
        TitleManager titleManager = new DefaultTitleManager(data,res);
        EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0, 10, 0)));


        titleManager.setTitle("TEST2");


        assertTrue("The title has not been successfully set to TEST2",titleManager.getTitleAsString().equals("TEST2"));

    }

    @Test
    public void testInvalidTitle(){
        String[] tempTitles = {"TEST1", "TEST2","TEST3"};
        int[] tempVals = {1,2,3};

        when(res.getStringArray(R.array.TitleStringArray)).thenReturn(tempTitles);
        when(res.getIntArray(R.array.TitleLevelArray)).thenReturn(tempVals);
        TitleManager titleManager = new DefaultTitleManager(data,res);
        EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0, 10, 0)));
        titleManager.setTitle("TEST2");
        assertTrue("The title was not successfully set to TEST2",titleManager.getTitleAsString().equals("TEST2"));

        titleManager.setTitle("INVALID");
        assertTrue("The title was changed",titleManager.getTitleAsString().equals("TEST2"));

    }

    @Test
    public void testOptions(){
            String[] tempTitles = {"TEST1", "TEST2","TEST3"};
            int[] tempVals = {1,2,3};

            when(res.getStringArray(R.array.TitleStringArray)).thenReturn(tempTitles);
            when(res.getIntArray(R.array.TitleLevelArray)).thenReturn(tempVals);
            TitleManager titleManager = new DefaultTitleManager(data,res);
            EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0, 2, 0)));

            List<Title> testList = titleManager.getTitleOptions();

            assertTrue("First element string is not equal", testList.get(0).getString().equals("TEST1"));
            assertTrue("Second element string is not equal", testList.get(1).getString().equals("TEST2"));
            assertTrue("List is not of correct length", testList.size() == 2);
    }

    @Test
    public void testEventUpdating() {
        String[] tempTitles = {"TEST1", "TEST2", "TEST3"};
        int[] tempVals = {1, 2, 3};

        when(res.getStringArray(R.array.TitleStringArray)).thenReturn(tempTitles);
        when(res.getIntArray(R.array.TitleLevelArray)).thenReturn(tempVals);
        TitleManager titleManager = new DefaultTitleManager(data, res);
        EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0, 1, 0)));
        assertTrue("Expected to have only 1 title option", titleManager.getTitleOptions().size() == 1 );


        EventDispatch.dispatchEvent((new UserUpdateEvent(new User(0,3,0))));
        assertTrue("Expected to have 3 title options", titleManager.getTitleOptions().size() == 3 );

    }
}
