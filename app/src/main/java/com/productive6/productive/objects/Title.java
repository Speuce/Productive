package com.productive6.productive.objects;

public class Title {

    private String title;
    private int levelRequirement;

    public Title(String title, int levelRequirement){
        this.title = title;
        this.levelRequirement = levelRequirement;
    }

    public int getLevelRequirement(){return levelRequirement;}
    public String getString(){return title;}

}
