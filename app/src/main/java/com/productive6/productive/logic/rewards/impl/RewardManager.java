package com.productive6.productive.logic.rewards.impl;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;
import com.productive6.productive.objects.events.user.*;
import com.productive6.productive.objects.User;


public class RewardManager implements IRewardManager, ProductiveListener{

    protected int coinWeight; //weight value for how many coins are awarded
    protected int experienceWeight; //weight value for how much XP is added
    protected int levelUpValue; //the amount of XP needed for a level up to occur

    protected User person;
    protected IUserManager data;
    /**
     * @param data a UserManager object used to update the user information in the database
     */
    public RewardManager(IUserManager data, int xpWeight, int coinWeight, int levelUpValue){
        EventDispatch.registerListener(this);

        experienceWeight = xpWeight;
        this.coinWeight = coinWeight;
        this.levelUpValue = levelUpValue;
        this.data = data;
        person = null;
    }

    /**
     * @return integer representation of current user level
     * returns 0 if person has not been initialized by the database
     */
    public int getLevel() {
        int level = 0;
        if (person != null)
            level = person.getLevel();
        return level;
    }
    /**
     * @return integer representation of current user experience
     * returns 0 if person has not been initialized by the database
     */
    public int getExperience(){
        int xp = 0;
        if(person != null)
            xp = person.getExp();
        return xp;
    }

    /**
     * @return an integer representation of the amount of experience required for a level up
     */
    public int getLevelUpValue(){return levelUpValue;}

    /**
     * @return integer representation of current user coin count
     * returns 0 if person has not been initialized by the database
     */
    public int getCoins(){
        int coins = 0;
            if(person != null)
                 coins =  person.getCoins();
        return coins;
    }

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

        int newVal = calculateNewCoins(completedTask);

        person.setCoins(newVal);
    }

    /**
     * Updates experience using a formula based off the priority of the task
     * levels the user up if the user has reached the experience threshold give by levelUpValue
     * @param completedTask the task that was completed that the user is receiving coins for
     */
    private void updateExperience(Task completedTask){

        int newXP = calculateNewXP(completedTask);

        person.setExp(newXP);

        if(person.getExp() >= levelUpValue){
            levelUp();
        }

    }

    protected int calculateNewXP(Task completedTask){
        return person.getExp() + completedTask.getPriority() * experienceWeight;
    }

    protected int calculateNewCoins(Task completedTask){
        return person.getCoins() + completedTask.getDifficulty() * coinWeight;
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
    public void taskCompletedEventHandler(TaskCompleteEvent event){
        taskCompleted(event);
    }

    /**
    * Helper method for taskCompletedEventHandler
     */
    protected void taskCompleted(TaskCompleteEvent event){
        updateRewards(event.getTask());
    }

    /**
     * After the user is updated this object is notified
     * @param event: the event that has this method handles
     * dispatches event to show that title has been initialized
     */
    @ProductiveEventHandler
    public void initializeValuesEventHandler(UserLoadedEvent event){
        initializeValues(event);
    }

    /**
     * Helper method for initializeValuesEventHandler
     */
    protected void initializeValues(UserLoadedEvent event){
        person = event.getUser();
        EventDispatch.dispatchEvent(new UserTitleInitialized());
    }

}


