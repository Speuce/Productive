package com.productive6.productive.logic.util;

import java.time.format.DateTimeFormatter;

public abstract class CalenderUtilities {

    /**
     * For formatting dates for the user
     */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String getDateWithSuffix(int day) {
        switch (day % 10) {
            case 1:  return day + "st";
            case 2:  return day + "nd";
            case 3:  return day + "rd";
            default: return day + "th";
        }
    }

}
