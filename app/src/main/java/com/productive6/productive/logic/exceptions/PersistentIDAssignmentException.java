package com.productive6.productive.logic.exceptions;

/**
 * This exception should be thrown when persistent entities (annotated with the {@link androidx.room.Entity} interface)
 * are pre-maturely assigned an id, when one shouldn't have been assigned
 */
public class PersistentIDAssignmentException extends RuntimeException {

    public PersistentIDAssignmentException() {
    }
}
