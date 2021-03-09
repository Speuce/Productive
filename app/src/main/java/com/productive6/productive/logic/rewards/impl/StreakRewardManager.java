package com.productive6.productive.logic.rewards.impl;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.user.IUserManager;



public class StreakRewardManager extends RewardManager {

    public StreakRewardManager(IUserManager data, ITaskManager taskManager, int xpWeight, int coinWeight, int levelUpValue){
        super(data,xpWeight,coinWeight,levelUpValue);
    }

    


}
