package com.productive6.productive.integration;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.productive6.productive.logic.adapters.ICosmeticAdapter;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.ITitleManager;
import com.productive6.productive.logic.rewards.impl.DefaultTitleManager;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.logic.user.impl.PersistentSingleUserManager;
import com.productive6.productive.objects.Title;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.objects.events.user.UserUpdateEvent;
import com.productive6.productive.persistence.room.adapters.CosmeticConverter;
import com.productive6.productive.persistence.room.impl.InMemoryAndroidDataManager;
import com.productive6.productive.services.executor.IRunnableExecutor;
import com.productive6.productive.services.executor.impl.TestExecutor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TitleManagerIntTest {


    ITitleManager TitleManager;

    @Before
    public void init(){
        Context mContext = InstrumentationRegistry.getInstrumentation().getContext();
        IRunnableExecutor mRunnableExecutor = new TestExecutor();
        EventDispatch.clear();
        String[] tempTitles = {"TEST1", "TEST2", "TEST3"};
        int[] tempVals = {1, 2, 3};
        InMemoryAndroidDataManager data = new InMemoryAndroidDataManager(mContext, mRunnableExecutor, mock(ICosmeticAdapter.class));
        data.init();
        IUserManager userManager = new PersistentSingleUserManager(data);
        TitleManager = new DefaultTitleManager(userManager,tempTitles,tempVals);

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
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(UserLoadedEvent e){
                TitleManager.setTitle("TEST2");
                assertTrue("The title has not been successfully set to TEST2", TitleManager.getTitleAsString().equals("TEST2"));
            }
        });
    }

    @Test
    public void testInvalidTitle(){

        EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0, 10, 0)));
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(UserLoadedEvent e){
                TitleManager.setTitle("TEST2");
                assertTrue("The title was not successfully set to TEST2", TitleManager.getTitleAsString().equals("TEST2"));
                EventDispatch.registerListener(new ProductiveListener() {
                    @ProductiveEventHandler
                    public void handleEvent(UserLoadedEvent e){
                        TitleManager.setTitle("INVALID");
                        assertTrue("The title was changed", TitleManager.getTitleAsString().equals("TEST2"));
                    }
                });
            }
        });



    }

    @Test
    public void testOptions(){
        EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0, 2, 0)));
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(UserLoadedEvent e){
                List<Title> testList = TitleManager.getTitleOptions();
                assertTrue("First element string is not equal", testList.get(0).getString().equals("TEST1"));
                assertTrue("Second element string is not equal", testList.get(1).getString().equals("TEST2"));
                assertTrue("List is not of correct length", testList.size() == 2);
            }
        });
    }


    @Test
    public void testEventUpdating() {
        EventDispatch.dispatchEvent(new UserLoadedEvent(new User(0, 1, 0)));
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(UserLoadedEvent e){
                assertTrue("Expected to have only 1 title option", TitleManager.getTitleOptions().size() == 1 );
                EventDispatch.dispatchEvent((new UserUpdateEvent(new User(0,3,0))));
                EventDispatch.registerListener(new ProductiveListener() {
                    @ProductiveEventHandler
                    public void handleEvent(UserLoadedEvent e){
                        assertTrue("Expected to have 3 title options", TitleManager.getTitleOptions().size() == 3 );
                    }
                });
            }
        });
    }
}
