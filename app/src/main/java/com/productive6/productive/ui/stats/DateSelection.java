package com.productive6.productive.ui.stats;

/**
 * Represents a Date Range Selection by the user
 */
public enum DateSelection {

    SEVEN("7 Days", 7),
    FOURTEEN("14 Days", 14),
    ONE_MONTH("30 Days", 30);

    private final String display;
    private final int days;

    private DateSelection(String display, int days) {
        this.display = display;
        this.days = days;
    }

    public String getDisplay() {
        return display;
    }

    public int getDays() {
        return days;
    }
}
