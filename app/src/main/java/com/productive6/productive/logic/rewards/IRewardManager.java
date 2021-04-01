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
     * @Returns the user's current coin count as an int
     */
    int getCoins();

    /**
     * isInitialized
     * @return true if the manager has been initialized
     */
    boolean isInitialized();

}
