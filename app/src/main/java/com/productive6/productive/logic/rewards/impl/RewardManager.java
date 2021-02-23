package com.productive6.productive.logic.rewards.impl;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.user.UserManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;
import com.productive6.productive.objects.events.user.*;
import com.productive6.productive.objects.User;


public class RewardManager implements IRewardManager, ProductiveListener{

    private int coinWeight; //weight value for how many coins are awarded
    private int experienceWeight; //weight value for how much XP is added
    private int levelUpValue; //the amount of XP needed for a level up to occur

    private User person;
    private UserManager data;
    /**
     * @param data a UserManager object used to update the user information in the database
     */
    public RewardManager(UserManager data,int xpWeight, int coinWeight, int levelUpValue){
        experienceWeight = xpWeight;
        this.coinWeight = coinWeight;
        this.levelUpValue = levelUpValue;

        EventDispatch.registerListener(this);
        this.data = data;
        person = null;
    }

    /**
     * @return integer representation of current user level
     */
    public int getLevel(){return person.getLevel();}

    /**
     * @return integer representation of current user experience
     */
    public int getExperience(){return person.getExp();}

    /**
     * @return an integer representation of the amount of experience required for a level up
     */
    public int getLevelUpValue(){return levelUpValue;}

    /**
     * @return integer representation of current user coin count
     */
    public int getCoins(){return person.getCoins();}

    /**
     * Updates all rewards the TaskManager is responsible, updates level if experience is has exceeded
     * levelUpValue. Then once all rewards have been updated, a call is made to update the user object
     * in the database.
     * @param completedTask the task that was completed that the user should receive rewards for
     */
    private void updateRewards(Task completedTask){

        //update all reward 'currencies'
        updateCoins(completedTask);
        updateExperience(completedTask);

        //once all the reward updates are complete, do one user update to the database
        data.updateUser(person);
    }

    /**
     * Updates coins using a formula based off the priority of the task
     * @param completedTask the task that was completed that the user is receiving coins for
     */
    private void updateCoins(Task completedTask){
        person.setCoins(person.getCoins() + completedTask.getPriority()*coinWeight);
    }

    /**
     * Updates experience using a formula based off the priority of the task
     * levels the user up if the user has reached the experience threshold give by levelUpValue
     * @param completedTask the task that was completed that the user is receiving coins for
     */
    private void updateExperience(Task completedTask){

        person.setExp(person.getExp() + completedTask.getPriority()*experienceWeight);

        if(person.getExp() >= levelUpValue){
            levelUp();
        }

    }

    /**
     * Uses current experience to calculate and set the user's level and experience
     * based off of levelUpValue
     */
    private void levelUp(){

        int addedLevels = person.getExp() / levelUpValue;
        person.setExp(person.getExp()%levelUpValue); //where they are on next level
        person.setLevel(person.getLevel() + addedLevels); //adding levels

    }

    /**
     * Event handler for when a task is completed
     * makes sure that the completed task data is used to update rewards
     * @param event
     */
    @ProductiveEventHandler
    public void taskCompleted(TaskCompleteEvent event){
        updateRewards(event.getTask());
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

}


