package com.productive6.productive.logic.exceptions;

/**
 * Exception indicating that you, the developer, have attempted to access a database-connected field
 * before it has been loaded.
 * Use events as a solution.
 */
public class AccessBeforeLoadedException extends RuntimeException{

    public AccessBeforeLoadedException(String message) {
        super(message);
    }
}
