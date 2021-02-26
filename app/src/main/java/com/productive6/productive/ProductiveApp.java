package com.productive6.productive;

import android.app.Application;

import com.productive6.productive.logic.rewards.IRewardManager;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class ProductiveApp extends Application {

    @Inject
    IRewardManager rewardManager;

}
