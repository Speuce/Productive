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

import static org.mockito.Mockito.*;


public class TitleManagerTest {

    DummyTitleDataManager data = new DummyTitleDataManager();

    @Mock
    Resources res;

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void constructorTest(){
        String[] tempTitles = {"TEST1", "TEST2"};
        int[] tempVals = {1,2};

        when(res.getStringArray(R.array.TitleStringArray)).thenReturn(tempTitles);
        when(res.getIntArray(R.array.TitleLevelArray)).thenReturn(tempVals);

        TitleManager titleManager = new DefaultTitleManager(data,res);


    }


}
