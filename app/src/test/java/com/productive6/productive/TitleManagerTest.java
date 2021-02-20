package com.productive6.productive;
import com.productive6.productive.logic.rewards.TitleManager;
import com.productive6.productive.logic.rewards.impl.DefaultTitleManager;
import com.productive6.productive.persistence.dummy.DummyTitleDataManager;
import android.content.res.Resources;

import org.junit.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import com.productive6.productive.objects.*;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;



public class TitleManagerTest {

    DummyTitleDataManager data = new DummyTitleDataManager();

    @Mock
    Resources res;

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void testAllTitleStrings(){
        String[] tempTitles = {"TEST1", "TEST2","TEST3"};
        int[] tempVals = {1,2,3};

        when(res.getStringArray(R.array.TitleStringArray)).thenReturn(tempTitles);
        when(res.getIntArray(R.array.TitleLevelArray)).thenReturn(tempVals);
        data.setLevel(10000);


        TitleManager titleManager = new DefaultTitleManager(data,res);
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
        data.setLevel(10000);


        TitleManager titleManager = new DefaultTitleManager(data,res);
        List<Title> testList = titleManager.getTitleOptions();

        assertTrue("First element string is equal", testList.get(0).getLevelRequirement() == 1);
        assertTrue("First element string is equal", testList.get(1).getLevelRequirement() == 2);
        assertTrue("First element string is equal", testList.get(2).getLevelRequirement() == 3);

    }

    @Test
    public void testValidation(){
        String[] tempTitles = {"TEST1", "TEST2","TEST3"};
        int[] tempVals = {1,2,3};

        when(res.getStringArray(R.array.TitleStringArray)).thenReturn(tempTitles);
        when(res.getIntArray(R.array.TitleLevelArray)).thenReturn(tempVals);
        data.setLevel(10000);
        TitleManager titleManager = new DefaultTitleManager(data,res);




    }


}
