package com.productive6.productive.logic.rewards;

import com.productive6.productive.objects.Task;

import java.util.List;
import java.util.function.Consumer;

public interface IStreakRewardManager extends IRewardManager{

    void onStreak(Consumer<List<Task>> run);
    int getStreakLength();

}
