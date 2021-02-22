package com.productive6.productive.objects.injection;

import android.content.Context;

import com.productive6.productive.executor.AndroidExecutor;
import com.productive6.productive.executor.RunnableExecutor;
import com.productive6.productive.logic.task.TaskManager;
import com.productive6.productive.logic.task.impl.PersistentTaskManager;
import com.productive6.productive.logic.user.UserManager;
import com.productive6.productive.logic.user.impl.PersistentSingleUserManager;
import com.productive6.productive.persistence.datamanage.DataManager;
import com.productive6.productive.persistence.datamanage.PersistentDataManager;

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
    public RunnableExecutor provideExecutorService(){
        return new AndroidExecutor();
    }

    @Singleton
    @Provides
    public DataManager provideDataManager(@ApplicationContext Context context){
        DataManager d = new PersistentDataManager(context);
        d.init();
        return d;
    }

    @Singleton
    @Provides
    public TaskManager provideTaskManager(DataManager d, RunnableExecutor e){
        return new PersistentTaskManager(d, e);
    }

    @Singleton
    @Provides
    public UserManager provideUserManager(DataManager d, RunnableExecutor e){
        return new PersistentSingleUserManager(d, e );
    }




}
