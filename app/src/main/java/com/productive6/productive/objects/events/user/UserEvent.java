package com.productive6.productive.objects.events.user;

import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.ProductiveEvent;

/**
 * Any event pertaining to a user
 */
public abstract class UserEvent extends ProductiveEvent {

    /**
     * The user who this event pertains to
     */
    private User user;

    /**
     * @param u The user who this event pertains to
     */
    public UserEvent(User u) {
        this.user = u;
    }

    /**
     * @return The user who this event pertains to
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user The user who this event pertains to
     */
    public void setUser(User user) {
        this.user = user;
    }
}
