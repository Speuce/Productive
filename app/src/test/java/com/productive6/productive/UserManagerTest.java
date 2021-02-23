package com.productive6.productive;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.exceptions.AccessBeforeLoadedException;
import com.productive6.productive.logic.exceptions.ObjectFormatException;
import com.productive6.productive.logic.executor.impl.TestExecutor;
import com.productive6.productive.logic.task.TaskManager;
import com.productive6.productive.logic.task.impl.PersistentTaskManager;
import com.productive6.productive.logic.user.UserManager;
import com.productive6.productive.logic.user.impl.PersistentSingleUserManager;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.objects.events.user.UserUpdateEvent;
import com.productive6.productive.persistence.dummy.DummyDataManager;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;
/**
 * Tests the logic-layer user manager with dummy database implementations.
 */
public class UserManagerTest {

    private UserManager userManager;

    private DummyDataManager data;

    @Before
    public void init(){
        data = new DummyDataManager();
        userManager = new PersistentSingleUserManager(data, new TestExecutor());
    }

    @Test
    public void testAccessBeforeLoaded(){
        assertThrows("User Manager failed to throw an access before loaded on getting current user before load is called!",
                AccessBeforeLoadedException.class, () -> userManager.getCurrentUser());
    }

    @Test
    public void testInvalidUpdate(){
        assertThrows("User Manager failed to catch an update with a un-indentified user!",
                ObjectFormatException.class, ()-> userManager.updateUser(new User()));
    }

    @Test
    public void testLoadCreatesUser(){
        //database is initially empty (dummy db)
        userManager.load();
        assertEquals("UserManager failed to create a new user on load with a blank db!",userManager.getCurrentUser().getId(), 1);
    }

    @Test
    public void testLoadEvent(){
        AtomicBoolean success = new AtomicBoolean(false);
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(UserLoadedEvent e){
                success.set(true);
            }
        });
        userManager.load();
        assertTrue("UserManager failed to trigger a user loaded event when necessary.",success.get());
    }

    @Test
    public void testUpdateEvent(){
        AtomicBoolean success = new AtomicBoolean(false);
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void handleEvent(UserUpdateEvent e){
                success.set(true);
            }
        });
        userManager.load();
        userManager.updateUser(userManager.getCurrentUser());
        assertTrue("UserManager failed to trigger a user updated event when necessary.",success.get());
    }


}
