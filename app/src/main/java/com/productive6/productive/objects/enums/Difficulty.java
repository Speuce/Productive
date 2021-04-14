package com.productive6.productive.objects.enums;

/**
 * Enum representing the difficulty of a tas
 */
public enum Difficulty {
    HARD("Hard"),
    MEDIUM("Medium"),
    EASY("Easy");

    /**
     * The string representation of the enum
     */
    private final String string;

    Difficulty(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
