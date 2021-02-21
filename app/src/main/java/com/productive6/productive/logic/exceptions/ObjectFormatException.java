package com.productive6.productive.logic.exceptions;

/**
 * An {@link RuntimeException} indicating that invalid fields were
 * passed into an domain specific object
 */
public class ObjectFormatException extends RuntimeException{

    /**
     * Construct a new object format exception with the given error message
     * @param message the message describing the exception
     */
    public ObjectFormatException(String message) {
        super(message);
    }
}
