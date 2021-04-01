package com.productive6.productive.objects.injection;

import android.content.Context;
import android.content.res.Resources;
import com.productive6.productive.R;

import com.productive6.productive.logic.adapters.ICosmeticAdapter;
import com.productive6.productive.logic.adapters.impl.DefaultCosmeticAdapter;
import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.rewards.IStreakRewardManager;
import com.productive6.productive.logic.rewards.impl.StreakRewardManager;
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
import com.productive6.productive.persistence.room.impl.PersistentAndroidDataManager;

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
    public ITaskManager provideTaskManager(IDataManager d, @ApplicationContext Context context){
        int configValues[] = new int[2];
        configValues[0] = context.getResources().getInteger(R.integer.minimumpriority);
        configValues[1] = context.getResources().getInteger(R.integer.minimumdifficulty);

        return new PersistentTaskManager(d,configValues);
    }


    @Singleton
    @Provides
    public ITaskSorter provideTaskSorter(IDataManager d){
        return new PersistentTaskSorter(d);
    }

    @Singleton
    @Provides
    public IUserManager provideUserManager(IDataManager d){
        return new PersistentSingleUserManager(d);
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

    @Singleton
    @Provides
    public IStreakRewardManager provideIStreakRewardManager(IUserManager data, ITaskSorter sort, ITaskManager taskManager, @ApplicationContext Context context){

        int[] configValues = new int[5];

        configValues[0] = context.getResources().getInteger(R.integer.experienceweight);
        configValues[1] = context.getResources().getInteger(R.integer.coinsweight);
        configValues[2] = context.getResources().getInteger(R.integer.levelupvalue);
        configValues[3] = context.getResources().getInteger(R.integer.streakhours);

        IStreakRewardManager rm = new StreakRewardManager(data,sort, taskManager ,configValues);
        return rm;
    }

    @Singleton
    @Provides
    public IRewardManager provideIRewardManager(IStreakRewardManager streak){
        return  streak;
    }


    @Singleton
    @Provides
    public ICosmeticAdapter provideICosmeticAdapter( @ApplicationContext Context context){
        Resources res = context.getResources();
        int keyAdapter[][] = new int[2][];

        keyAdapter[0] = res.getIntArray(R.array.CosmeticIdArray);
        keyAdapter[1] = res.getIntArray(R.array.CosmeticResourceIdArray);

        int costArr[] = res.getIntArray(R.array.CosmeticCostArray);
        String names[] = res.getStringArray(R.array.CosmeticNameArray);

        ICosmeticAdapter cosmeticAdapter = new DefaultCosmeticAdapter(keyAdapter,costArr,names);

        return cosmeticAdapter;
    }

}
