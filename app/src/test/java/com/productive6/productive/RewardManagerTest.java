package com.productive6.productive;


import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.rewards.impl.RewardManager;
import com.productive6.productive.logic.user.UserManager;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.objects.events.user.UserUpdateEvent;
import com.productive6.productive.persistence.datamanage.DataManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


public class RewardManagerTest {

    IRewardManager rewardmanager;

    @Mock
    UserManager data;



    @Before
    public void init(){
        rewardmanager = new RewardManager(data);
    }

    @Test
    public void testAddExperience(){}






}
