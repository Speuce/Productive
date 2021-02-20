package com.productive6.productive.objects.events.user;

import com.productive6.productive.objects.User;

/**
 * Event Called when any user information update is sent to the database
 */
public class UserUpdateEvent extends UserEvent{
    /**
     * @param u The user who this event pertains to
     */
    public UserUpdateEvent(User u) {
        super(u);
    }
}
