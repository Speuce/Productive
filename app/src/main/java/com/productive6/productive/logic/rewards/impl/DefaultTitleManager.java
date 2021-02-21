package com.productive6.productive.logic.rewards.impl;


import android.content.res.Resources;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.user.UserManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.productive6.productive.objects.events.user.*;
import com.productive6.productive.R;
import com.productive6.productive.objects.Title;
import com.productive6.productive.logic.rewards.TitleManager;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;

public class DefaultTitleManager implements TitleManager, ProductiveListener{

    private User person;
    private UserManager data;
    private List<Title> titles;

    public DefaultTitleManager(UserManager data, Resources res){

        EventDispatch.registerListener(this);
        this.data = data;
        titles = getAllTitles(res);
        person = null;
    }

    /**
     * Creates a List of all possible title options for the user
     * @return List<Title> of all options the user has for their displayed title
     */
    public List<Title> getTitleOptions(){

        List<Title> options = new LinkedList<Title>();


        titles.iterator().forEachRemaining(curr -> {

            if(curr.getLevelRequirement() <= getLevel())
                options.add(new Title(curr.getString(),curr.getLevelRequirement()));

        });
        return options;
    }

    /**
     * Returns a String repersentation of the currently selected title
     * @return String representing the current title
     */
    public String getTitleAsString(){return person.getSelectedTitle();}

    /**
     * @return integer representing current level
     */
    private int getLevel(){return person.getLevel();}


    /**
     * Checks if a new title is valid and sets it if it is
     * @param newTitle: String representing new Title
     */
    @Override
    public void setTitle(String newTitle) {

        if(validateTitle(newTitle)) {
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
    private List<Title> getAllTitles(Resources titleResources){
        List<Title> titleList = new LinkedList<Title>();

            try { //make sure that application can handle bad resource files
                String[] titleStrings = titleResources.getStringArray(R.array.TitleStringArray);
                int[] titleLevels = titleResources.getIntArray(R.array.TitleLevelArray);

                for (int i = 0; (i < titleStrings.length) && (i < titleLevels.length); i++) {
                    titleList.add(new Title(titleStrings[i],titleLevels[i]));
                }
            }catch(Exception e){ //if an exception occurs while trying to access titles return an empty list of titles
                titleList = new LinkedList<Title>();
            }

        return titleList;
    }

    @ProductiveEventHandler
    public void initializeValues(UserLoadedEvent e){
        person = e.getUser();
    }

    @ProductiveEventHandler
    public void updateUser(UserUpdateEvent e){
        person = e.getUser();
    }

} //end class
