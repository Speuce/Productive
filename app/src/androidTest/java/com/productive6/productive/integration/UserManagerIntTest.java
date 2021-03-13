package com.productive6.productive.integration;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.exceptions.AccessBeforeLoadedException;
import com.productive6.productive.logic.exceptions.ObjectFormatException;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.logic.user.impl.PersistentSingleUserManager;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.objects.events.user.UserUpdateEvent;
import com.productive6.productive.persistence.datamanage.impl.InMemoryAndroidDataManager;
import com.productive6.productive.persistence.executor.IRunnableExecutor;
import com.productive6.productive.persistence.executor.impl.TestExecutor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the logic-layer user manager with dummy database implementations.
 */

@RunWith(AndroidJUnit4.class)
public class UserManagerIntTest {


    private IUserManager userManager;

    @Before
    public void init(){
        Context mContext = InstrumentationRegistry.getInstrumentation().getContext();
        IRunnableExecutor mRunnableExecutor = new TestExecutor();
        InMemoryAndroidDataManager data = new InMemoryAndroidDataManager(mContext, mRunnableExecutor);
        data.init();
        userManager = new PersistentSingleUserManager(data);
    }

    /**
     * Check if User Manager failed to throw an access before loaded on getting current user before load is called
     */
    @Test(expected = AccessBeforeLoadedException.class)
    public void testAccessBeforeLoaded(){
        userManager.getCurrentUser();
    }

    /**
     * Check if User Manager failed to catch an update with a un-indentified user
     */
    @Test(expected = ObjectFormatException.class)
    public void testInvalidUpdate(){
        userManager.updateUser(new User());
    }

    @Test
    public void testLoadCreatesUser(){
        //database is initially empty (dummy db)
        userManager.load();
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(UserLoadedEvent e){
                assertEquals("UserManager failed to create a new user on load with a blank db!",userManager.getCurrentUser().getId(), 1);
            }
        });

    }

    @Test
    public void testLoadEvent(){
        AtomicBoolean success = new AtomicBoolean(false);
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(UserLoadedEvent e){
                success.set(true);
                assertTrue("UserManager failed to trigger a user loaded event when necessary.",success.get());
            }
        });
        userManager.load();

    }

    @Test
    public void testUpdateEvent(){
        AtomicBoolean success = new AtomicBoolean(false);
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(UserLoadedEvent e){
                userManager.updateUser(userManager.getCurrentUser());
            }
        });
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(UserUpdateEvent e){
                success.set(true);
                assertTrue("UserManager failed to trigger a user updated event when necessary.",success.get());
            }
        });
        userManager.load();
    }


}
