package com.productive6.productive.logic.rewards.impl;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.ITitleManager;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.objects.Title;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.objects.events.user.UserUpdateEvent;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DefaultTitleManager implements ITitleManager, ProductiveListener{

    private User person;
    private IUserManager data;
    private List<Title> titles;

    public DefaultTitleManager(IUserManager data, String[] titleString, int[] titleLevels){

        EventDispatch.registerListener(this);
        this.data = data;
        titles = getAllTitles(titleString,titleLevels);

    }

    /**
     * Creates a List of all possible title options for the user
     * @return List<Title> of all options the user has for their displayed title
     */
    public List<Title> getTitleOptions(){

        List<Title> options = new LinkedList<Title>();

        if(person != null) {
            titles.iterator().forEachRemaining(curr -> {

                if (curr.getLevelRequirement() <= getLevel())
                    options.add(new Title(curr.getString(), curr.getLevelRequirement()));

            });
        }
        return options;
    }

    /**
     * Returns a String representation of the currently selected title
     * @return String representing the current title, returns an empty string
     * if person has not been initialized by the database
     */
    public String getTitleAsString(){

        String str = "";

        if(person != null)
            str = person.getSelectedTitle();

        return str;
    }

    /**
     * @return integer representing current level
     */
    private int getLevel() {
        return person.getLevel();
    }


    /**
     * Checks if a new title is valid and sets it if it is
     * @param newTitle: String representing new Title
     */
    @Override
    public void setTitle(String newTitle) {

        if(validateTitle(newTitle) && person != null) {
            person.setSelectedTitle(newTitle);
            data.updateUser(person);
        }
    }

    /**
     *
     * @param str checks is str is a valid title option for the user
     * @return returns true if it is, false if not
     */
    private boolean validateTitle(String str){
        boolean valid = false;
        Iterator<Title> options = getTitleOptions().listIterator();

        while(options.hasNext() && !valid){
            if(options.next().getString().equals(str))
                valid = true;
        }

        return valid;
    }

    /**
    * Returns a list of all the titles in the resources files
    * @return a list of the all the titles that can be accessed in
    * res/values/titles.xml
     */
    private List<Title> getAllTitles(String[] titleStrings, int[] titleLevels){
        List<Title> titleList = new LinkedList<Title>();

            for (int i = 0; (i < titleStrings.length) && (i < titleLevels.length); i++) {
                    titleList.add(new Title(titleStrings[i],titleLevels[i]));
            }

        return titleList;
    }

    /**
     * After the user is updated this object is notified
     * @param e: the event that has this method handles
     */
    @ProductiveEventHandler
    public void initializeValues(UserLoadedEvent e){
        person = e.getUser();
    }

    /**
     * After the user is updated this object is notified
     * @param e: the event that has this method handles
     */
    @ProductiveEventHandler
    public void updateUser(UserUpdateEvent e){
        person = e.getUser();
    }

} //end class
