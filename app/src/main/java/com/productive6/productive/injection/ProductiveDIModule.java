package com.productive6.productive.injection;

import android.content.Context;
import android.provider.ContactsContract;

import com.productive6.productive.executor.AndroidExecutor;
import com.productive6.productive.executor.RunnableExecutor;
import com.productive6.productive.logic.task.TaskManager;
import com.productive6.productive.logic.task.impl.PersistentTaskManager;
import com.productive6.productive.persistence.DataManager;
import com.productive6.productive.persistence.PersistentDataManager;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

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
    public TaskManager provideTaskManager(DataManager d, ExecutorService e){
        return new PersistentTaskManager(d, e);
    }


}
