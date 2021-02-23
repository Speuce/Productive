package com.productive6.productive.logic.user.impl;

import com.productive6.productive.logic.exceptions.ObjectFormatException;
import com.productive6.productive.logic.executor.IRunnableExecutor;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.exceptions.AccessBeforeLoadedException;
import com.productive6.productive.logic.user.UserManager;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.objects.events.user.UserUpdateEvent;
import com.productive6.productive.persistence.datamanage.DataManager;

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
    private final DataManager data;

    /**
     * The {@link IRunnableExecutor} for running functions synchronous/asynchronously
     */
    private final IRunnableExecutor executor;

    /**
     * The current user using the app.
     */
    private User currentUser;

    public PersistentSingleUserManager(DataManager data, IRunnableExecutor executor) {
        this.data = data;
        this.executor = executor;

    }

    /**
     * Reads data from the data layer and populates local variables.
     *
     * getCurrentUser will throw {@link AccessBeforeLoadedException}
     * if this is not called before the first getCurrentUser call.
     */
    public void load(){
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
                //create a default user. Iteration 1 smelly stuff. Iteration 2/3 will have a user creation UI
                u = new User();
                u.setCoins(0);
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
        if(u.getId() == 0){
            throw new ObjectFormatException("Attempted to update a user without the user having an associated id first!");
        }
        executor.runASync(() -> {data.user().updateUser(u);});
        EventDispatch.dispatchEvent(new UserUpdateEvent(u));
    }
}
