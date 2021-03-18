package com.productive6.productive.logic.util;

import com.productive6.productive.objects.Task;

/**
 * Various utility methods with tasks
 */
public abstract class TaskUtilities {

    /**
     * Reads off the tasks priority integer value and converts it to something the user can make use of.
     * @param t the task to get the priorty of
     * @return a human readable string value.
     */
    public static String getPriorityInString(Task t) {
        String priorityInString = "unknown";

        if (t.getPriority() == 1) {
            priorityInString = "High";
        } else if (t.getPriority() == 2) {
            priorityInString = "Medium";
        } else if (t.getPriority() == 3){
            priorityInString = "Low";
        }
        return priorityInString;
    }

    /**
     * Reads off the tasks difficult integer value and converts it to something the user can make use of.
     * @param t the task to get the difficulty of
     * @return a human readable string value.
     */
    public static String getDifficultyInString(Task t) {
        String difficultyInString = "unknown";

        if (t.getDifficulty() == 1) {
            difficultyInString = "Hard";
        } else if (t.getDifficulty() == 2) {
            difficultyInString = "Medium";
        } else if (t.getPriority() == 3){
            difficultyInString = "Easy";
        }
        return difficultyInString;
    }
}
