package com.productive6.productive.logic.user.impl;

import com.productive6.productive.logic.exceptions.ObjectFormatException;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.exceptions.AccessBeforeLoadedException;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.system.SystemLoadedEvent;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.objects.events.user.UserUpdateEvent;
import com.productive6.productive.persistence.datamanage.IDataManager;

/**
 * {@link IUserManager} implementation that persists a single (main user).
 *
 * TODO: add multi user support
 */
public class PersistentSingleUserManager implements IUserManager {

    /**
     * The Datamanaer to interface with the data layer
     */
    private final IDataManager data;

    /**
     * The current user using the app.
     */
    private User currentUser;

    public PersistentSingleUserManager(IDataManager data) {
        this.data = data;

    }

    @Override
    public void load(){
        loadCurrentUser();
    }

    /**
     * Loads the current user, or creates one if there is none.
     */
    private void loadCurrentUser(){
        data.user().getAllUsers(users -> {
            if(users.isEmpty()){
                //create a default user.
                currentUser = new User();
                currentUser.setCoins(0);
                data.user().insertUser(currentUser, () ->{
                    EventDispatch.dispatchEvent(new UserLoadedEvent(currentUser));
                    EventDispatch.dispatchEvent(new SystemLoadedEvent());
                });
            }else{
                currentUser = users.get(0);
                EventDispatch.dispatchEvent(new UserLoadedEvent(currentUser));
                EventDispatch.dispatchEvent(new SystemLoadedEvent());
            }

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
        data.user().updateUser(u, () ->EventDispatch.dispatchEvent(new UserUpdateEvent(u)));
    }
}
