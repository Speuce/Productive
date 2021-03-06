package com.productive6.productive.ui;

import android.app.Application;

import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.rewards.IStreakRewardManager;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class ProductiveApp extends Application {

    @Inject
    IStreakRewardManager streakRewardManager;


}
