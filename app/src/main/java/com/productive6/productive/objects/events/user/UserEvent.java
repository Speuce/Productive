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
    private User u;

    /**
     * @param u The user who this event pertains to
     */
    public UserEvent(User u) {
        this.u = u;
    }

    /**
     * @return The user who this event pertains to
     */
    public User getU() {
        return u;
    }

    /**
     * @param u The user who this event pertains to
     */
    public void setU(User u) {
        this.u = u;
    }
}
