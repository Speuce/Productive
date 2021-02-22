package com.productive6.productive.persistence.dummy;

public class DummyTitleDataManager {

    private int level;
    private int experience;
    String currTitle;

    public void init(){
        level = 0;
        experience = 0;
        currTitle = "";
    }

    //getters and setters for

    public int getLevel(){return level;}

    public int getExperience(){return experience;}

    public void setLevel(int newLevel){level = newLevel;}

    public void setExperience(int newExperience){experience = newExperience;}

    public String getTitle(){return currTitle;}

    public void setTitle(String newTitle){currTitle = newTitle; }

}
