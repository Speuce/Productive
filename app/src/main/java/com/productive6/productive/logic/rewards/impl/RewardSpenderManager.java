package com.productive6.productive.logic.rewards.impl;

import com.productive6.productive.logic.rewards.IRewardSpenderManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.logic.user.IUserManager;

public class RewardSpenderManager extends StreakRewardManager implements IRewardSpenderManager {

    public RewardSpenderManager(IUserManager data, ITaskSorter taskSorter,
                                ITaskManager taskManager, int[] configValues)
    {
        super(data,taskSorter,taskManager,configValues);
    }

    @Override
    public void spendCoins(int numSpent) {
            person.setCoins(person.getCoins() - numSpent);
            data.updateUser(person);
    }

    @Override
    public boolean canSpend(int num) {
        return person.getCoins() >= num;
    }
}

