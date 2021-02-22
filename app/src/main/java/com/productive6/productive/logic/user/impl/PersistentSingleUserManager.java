package com.productive6.productive.logic.user.impl;

import com.productive6.productive.executor.RunnableExecutor;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.exceptions.AccessBeforeLoadedException;
import com.productive6.productive.logic.user.UserManager;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.objects.events.user.UserUpdateEvent;
import com.productive6.productive.persistence.datamanage.IDataManager;

import java.util.List;

/**
 * {@link com.productive6.productive.logic.user.UserManager} implementation that persists a single (main user).
 *
 * TODO: add multi user support
 */
public class PersistentSingleUserManager implements UserManager {

    /**
     * The Datamanaer to interface with the data layer
     */
    private final IDataManager data;

    /**
     * The {@link RunnableExecutor} for running functions synchronous/asynchronously
     */
    private final RunnableExecutor executor;

    /**
     * The current user using the app.
     */
    private User currentUser;

    public PersistentSingleUserManager(IDataManager data, RunnableExecutor executor) {
        this.data = data;
        this.executor = executor;
        loadCurrentUser();
    }

    /**
     * Loads the current user, or creates one if there is none.
     */
    private void loadCurrentUser(){
        executor.runASync(() -> {
            List<User> users = data.user().getAllUsers();
            User u;
            if(users.isEmpty()){
                u = new User();
                u.setCoins(10);
                data.user().insertUser(u);
            }else{
                u = users.get(0);
            }
            executor.runSync(() ->{
                currentUser = u;
                EventDispatch.dispatchEvent(new UserLoadedEvent(currentUser));
            });
        });
    }

    @Override
    public User getCurrentUser() {
        if(currentUser == null){
            throw new AccessBeforeLoadedException("WHoa. Hold your horses there. I have no user to give you. Listen to UserLoadedEvent first");
        }
        return currentUser;
    }

    @Override
    public void updateUser(User u) {
        executor.runASync(() -> {data.user().updateUser(u);});
        EventDispatch.dispatchEvent(new UserUpdateEvent(u));
    }
}
