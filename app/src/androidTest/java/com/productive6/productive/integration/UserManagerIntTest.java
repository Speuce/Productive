package com.productive6.productive.integration;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.productive6.productive.logic.adapters.ICosmeticAdapter;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.exceptions.AccessBeforeLoadedException;
import com.productive6.productive.logic.exceptions.ObjectFormatException;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.logic.user.impl.PersistentSingleUserManager;
import com.productive6.productive.objects.Cosmetic;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.objects.events.user.UserUpdateEvent;
import com.productive6.productive.persistence.datamanage.IDataManager;
import com.productive6.productive.persistence.room.impl.InMemoryAndroidDataManager;
import com.productive6.productive.services.executor.IRunnableExecutor;
import com.productive6.productive.services.executor.impl.TestExecutor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the logic-layer user manager with real database implementations.
 */

@RunWith(AndroidJUnit4.class)
public class UserManagerIntTest {


    private IUserManager userManager;

    @Mock
    ICosmeticAdapter cosmeticAdapter;

    private IDataManager data;

    @Before
    public void init(){
        EventDispatch.clear();

        Context mContext = InstrumentationRegistry.getInstrumentation().getContext();
        IRunnableExecutor mRunnableExecutor = new TestExecutor();
        cosmeticAdapter = mock(ICosmeticAdapter.class);
        data = new InMemoryAndroidDataManager(mContext, mRunnableExecutor, cosmeticAdapter);
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
                assertNotNull("UserManager called loaded event with a NULL user >:(",e.getUser());
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

    /**
     * Tests the update of a users cosmetics
     */
    @Test
    public void testCosmeticUpdate(){

        final Cosmetic cosmetic = new Cosmetic(2, 1, 1,  "name");
        when(cosmeticAdapter.idToCosmetic(any(Integer.class))).thenAnswer(i ->{
            int val = i.getArgument(0);
            assertEquals("Cosmetic adapter call gave an unknown integer value!", val, 2);
            return cosmetic;
        });

        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void onLoaded(UserLoadedEvent e){
                try{

                    e.getUser().addToOwnedCosmetics(cosmetic);
                    e.getUser().setFavouriteCosmetic(cosmetic);
                    userManager.updateUser(e.getUser());
                }catch(Exception err){
                    err.printStackTrace();
                }

            }

            @ProductiveEventHandler
            public void onUpdateCompleted(UserUpdateEvent e){
                //pull directly from db and assert that everything is OK
                data.user().getAllUsers(u ->{
                    assertFalse("User Data Manager failed to return anything...", u.isEmpty());
                    assertEquals("user get in db gave the WRONG cosmetic..",u.get(0).getFavouriteCosmetic(), cosmetic);
                    assertEquals("user get in db gave the WRONG cosmetic..",u.get(0).getOwnedCosmetics().get(0), cosmetic);
                });
            }
        });
        userManager.load();

    }


}
