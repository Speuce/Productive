package com.productive6.productive.logic.exceptions;

/**
 * An {@link RuntimeException} indicating that invalid fields were
 * passed into an {@link com.productive6.productive.objects.Task} object
 */
public class TaskFormatException extends RuntimeException{

    /**
     * Construct a new task format exception with the given error message
     * @param message the message describing the exception
     */
    public TaskFormatException(String message) {
        super(message);
    }
}
