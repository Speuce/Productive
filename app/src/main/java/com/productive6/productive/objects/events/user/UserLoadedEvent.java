package com.productive6.productive.objects.events.user;

import com.productive6.productive.objects.User;

/**
 * Event Called when user information is loaded and ready for use.
 */
public class UserLoadedEvent extends UserEvent{
    /**
     * @param u The user who this event pertains to
     */
    public UserLoadedEvent(User u) {
        super(u);
    }
}
