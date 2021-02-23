package com.productive6.productive.logic.rewards;

import java.util.List;
import com.productive6.productive.objects.Title;

public interface ITitleManager {

    /*
    * Returns a list of all the title options that a user has for their given level
    * @return a List of all title options for the user
    * */
    List<Title> getTitleOptions();

    /*
     * set the user's title to be a new title
     * @param newTitle : the new title that the user has requested
     *
     * */
    void setTitle(String newTitle);

    /*
    * Returns the user's current title
    * @return the title the user currently has set
    * */
    String getTitleAsString();

}
