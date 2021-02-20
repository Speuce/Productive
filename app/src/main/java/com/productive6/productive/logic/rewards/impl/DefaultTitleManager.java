package com.productive6.productive.logic.rewards.impl;


import android.content.res.Resources;

import java.util.LinkedList;
import java.util.List;

import com.productive6.productive.R;
import com.productive6.productive.objects.Title;
import com.productive6.productive.logic.rewards.TitleManager;

public class DefaultTitleManager {
    public final int PLACEHOLDER_LEVEL = 25;

    List<Title> titles;

    public DefaultTitleManager(Resources res){
        titles = getAllTitles(res);
    }
    

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
