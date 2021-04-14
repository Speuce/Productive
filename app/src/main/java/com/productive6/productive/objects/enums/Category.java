package com.productive6.productive.objects.enums;

/**
 * Enum representing the difficulty of a tas
 */
public enum Category {
    UNCATEGORIZED("Uncategorized"),
    SCHOOL("School"),
    HOME("Home"),
    WORK("Work"),
    PERSONAL("Personal"),
    SOCIAL("Social");

    /**
     * The string representation of the enum
     */
    private final String string;

    Category(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
