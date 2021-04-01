package com.productive6.productive.logic.rewards;

public interface IRewardSpenderManager extends IStreakRewardManager{

    /**
     * Takes an integer and spends that many coins
     * @param numSpent The number of coins spent
     */
    void spendCoins(int numSpent);

    /**
     *
     * @param num number of coins the user wants to spend
     * @return true if the user can spend that many coins
     */
    boolean canSpend(int num);

}
