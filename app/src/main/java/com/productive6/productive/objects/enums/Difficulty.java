package com.productive6.productive.objects.enums;

/**
 * Enum representing the difficulty of a tas
 */
public enum Difficulty {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");


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
