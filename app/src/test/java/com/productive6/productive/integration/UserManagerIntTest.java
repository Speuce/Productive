package com.productive6.productive.integration;

import android.content.Context;

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
import com.productive6.productive.persistence.datamanage.dummy.DummyDataManager;
import com.productive6.productive.persistence.datamanage.impl.InMemoryAndroidDataManager;
import com.productive6.productive.persistence.executor.IRunnableExecutor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Tests the logic-layer user manager with dummy database implementations.
 */
public class UserManagerIntTest {

    @Mock
    private Context mContext = mock(Context.class);
    @Mock
    private IRunnableExecutor mRunnableExecutor = mock(IRunnableExecutor.class);

    private IUserManager userManager;

    private InMemoryAndroidDataManager data;

    @Before
    public void init(){
        data = new InMemoryAndroidDataManager(mContext,mRunnableExecutor);
        data.init();
        userManager = new PersistentSingleUserManager(data);
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
