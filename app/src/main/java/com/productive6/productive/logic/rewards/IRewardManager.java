package com.productive6.productive.logic.rewards;


/**
 * Interface for RewardManager
 * Manages the coin, experience and level values for the user
 */
public interface IRewardManager {

    /**
     * @return the users current level as an int
     */
    int getLevel();

    /**
     * @Returns the user's current experience value as an int
     */
    int getExperience();

    /**
     * @return the amount of experience at which a levelup occurs
     * can be used to calculate how far a user is through a level
     * i.e. if getExperience() = 50 and getLevelScalingValue() = 100
     * => user is 50% done the level
     */
    int getLevelUpValue();

    /**
     * @Returns the user's current coin count as an int
     */
    int getCoins();

    /**
     * isInitialized
     * @return true if the manager has been initialized
     */
    boolean isInitialized();

}
