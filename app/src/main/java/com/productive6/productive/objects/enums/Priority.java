package com.productive6.productive.objects.enums;

/**
 * Represents the users selected priority of a task
 */
public enum Priority {

    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    private final String string;

    Priority(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
