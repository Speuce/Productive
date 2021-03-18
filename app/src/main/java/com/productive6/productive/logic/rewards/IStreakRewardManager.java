package com.productive6.productive.logic.rewards;

import com.productive6.productive.objects.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

public interface IStreakRewardManager extends IRewardManager{

    int getStreakConstant();
    int inStreakTime(List<Task> tasks, LocalDateTime currTime);

}
