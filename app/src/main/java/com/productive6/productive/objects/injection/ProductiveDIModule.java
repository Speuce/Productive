package com.productive6.productive.objects.injection;

import android.content.Context;
import android.content.res.Resources;
import com.productive6.productive.R;

import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.rewards.impl.RewardManager;
import com.productive6.productive.logic.statstics.ICoinsStatsManager;
import com.productive6.productive.logic.statstics.ITaskStatsManager;
import com.productive6.productive.logic.statstics.IXPStatsManager;
import com.productive6.productive.logic.statstics.impl.StatsManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.logic.task.impl.PersistentTaskSorter;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.services.executor.IRunnableExecutor;
import com.productive6.productive.services.executor.impl.AndroidExecutor;
import com.productive6.productive.logic.rewards.ITitleManager;
import com.productive6.productive.logic.rewards.impl.DefaultTitleManager;

import com.productive6.productive.logic.task.impl.PersistentTaskManager;
import com.productive6.productive.logic.user.impl.PersistentSingleUserManager;
import com.productive6.productive.persistence.datamanage.IDataManager;
import com.productive6.productive.persistence.datamanage.impl.PersistentAndroidDataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

/**
 * Provides singleton instances for dependency injection.
 */
@Module
@InstallIn(SingletonComponent.class)
public class ProductiveDIModule {


    @Singleton
    @Provides
    public IRunnableExecutor provideExecutorService(){
        return new AndroidExecutor();
    }

    @Singleton
    @Provides
    public IDataManager provideDataManager(@ApplicationContext Context context, IRunnableExecutor e){
        IDataManager d = new PersistentAndroidDataManager(context, e);
        d.init();
        return d;
    }

    @Singleton
    @Provides
    public ITaskManager provideTaskManager(IDataManager d){
        return new PersistentTaskManager(d);
    }


    @Singleton
    @Provides
    public ITaskSorter provideTaskSorter(IDataManager d){
        return new PersistentTaskSorter(d);
    }

    @Singleton
    @Provides
    public IUserManager provideUserManager(IDataManager d){
        IUserManager um = new PersistentSingleUserManager(d);
//        um.load();
        return um;
    }

    @Singleton
    @Provides
    public ITitleManager provideITitleManager(@ApplicationContext Context context, IUserManager userManager){
        Resources res = context.getResources();
        String[] titlesStrings = res.getStringArray(R.array.TitleStringArray);
        int[] levelArr = res.getIntArray(R.array.TitleLevelArray);
        ITitleManager tm = new DefaultTitleManager(userManager,titlesStrings,levelArr);
        return tm;
    }

    @Singleton
    @Provides
    public IRewardManager provideIRewardManager(IUserManager data, ITaskManager task, @ApplicationContext Context context){

        int levelUpValue = context.getResources().getInteger(R.integer.levelupvalue);
        int coinWeight = context.getResources().getInteger(R.integer.coinsweight);
        int xpWeight = context.getResources().getInteger(R.integer.experienceweight);

        return new RewardManager(data,task,xpWeight,coinWeight,levelUpValue);
    }

    @Singleton
    @Provides
    public StatsManager provideStatsManager(IDataManager data){
        return new StatsManager(data);
    }

    @Singleton
    @Provides
    public ITaskStatsManager provideTaskStats(StatsManager stats){
        return (ITaskStatsManager)stats;
    }

    @Singleton
    @Provides
    public ICoinsStatsManager provideCoinStats(StatsManager stats){
        return (ICoinsStatsManager) stats;
    }

    @Singleton
    @Provides
    public IXPStatsManager provideXPStats(StatsManager stats){
        return (IXPStatsManager) stats;
    }

}
