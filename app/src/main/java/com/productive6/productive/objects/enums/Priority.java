package com.productive6.productive.objects.enums;

/**
 * Represents the users selected priority of a task
 */
public enum Priority {
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low");

    private final String string;

    Priority(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
