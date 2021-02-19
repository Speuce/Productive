package com.productive6.productive.logic.user.impl;

import com.productive6.productive.executor.RunnableExecutor;
import com.productive6.productive.logic.user.UserManager;
import com.productive6.productive.objects.User;
import com.productive6.productive.persistence.datamanage.DataManager;

import java.util.List;
import java.util.function.Consumer;

/**
 * {@link com.productive6.productive.logic.user.UserManager} implementation that persists a single (main user).
 *
 * TODO: add multi user support
 */
public class PersistentSingleUserManager implements UserManager {

    /**
     * The Datamanaer to interface with the data layer
     */
    private DataManager data;

    /**
     * The {@link RunnableExecutor} for running functions sync/async hrounously
     */
    private RunnableExecutor executor;

    public PersistentSingleUserManager(DataManager data, RunnableExecutor executor) {
        this.data = data;
        this.executor = executor;
    }

    @Override
    public void getCurrentUser(Consumer<User> callback) {
        executor.runASync(() -> {
            List<User> users = data.user().getAllUsers();
            User u;
            if(users.isEmpty()){
                u = new User();
                data.user().insertUser(u);
            }else{
                u = users.get(0);
            }
            executor.runSync(() ->{callback.accept(u);});
        });
    }

    @Override
    public void updateUser(User u) {
        executor.runASync(() -> {data.user().updateUser(u);});
    }
}
