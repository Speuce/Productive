package com.productive6.productive.logic.util;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import com.productive6.productive.objects.Task;

public abstract class DateUtilities {

    public static boolean inStreakTime(List<Task> tasks, Task completedTask, int streakLength){

        boolean onStreak = false;

        Iterator<Task> taskIterator = tasks.iterator();
        while(taskIterator.hasNext() && !onStreak){
            Task curr = taskIterator.next();
            //calculate period
            Duration timeDifference = Duration.between(curr.getCompleted(),completedTask.getCompleted());
            int hours = (int) timeDifference.toHours();
            if(hours <= streakLength && curr.getId() != completedTask.getId()) //check if with streak window and different tasks
                onStreak = true;

        }
        return onStreak;
    }


}
