package com.productive6.productive.logic.rewards.impl;


import android.content.res.Resources;

import com.productive6.productive.logic.user.UserManager;
import com.productive6.productive.persistence.dummy.DummyTitleDataManager;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.productive6.productive.R;
import com.productive6.productive.objects.Title;
import com.productive6.productive.logic.rewards.TitleManager;
import com.productive6.productive.persistence.datamanage.DataManager;

public class DefaultTitleManager implements TitleManager{

    protected UserManager data;
    protected List<Title> titles;

    public DefaultTitleManager(UserManager data, Resources res){

        this.data = data;
        titles = getAllTitles(res);

    }

    public List<Title> getTitleOptions(){

        List<Title> options = new LinkedList<Title>();

        titles.iterator().forEachRemaining(curr -> {

            if(curr.getLevelRequirement() <= getLevel())
                options.add(new Title(curr.getString(),curr.getLevelRequirement()));

        });
        return options;
    }


    //ADD TESTING
    private void getLevel(Consumer<Integer> i ){
        data.getCurrentUser((user -> {i.accept(user.getLevel());}));
//        return data.getLevel();
    }

    public Title getTitle(){return new Title(data.getTitle(), 0);}

    public void setTitle(Title t){}

    /*
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


}
