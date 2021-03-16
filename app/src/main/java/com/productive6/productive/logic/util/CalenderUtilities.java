package com.productive6.productive.logic.util;

public abstract class CalenderUtilities {

    public static String getDateWithSuffix(int day) {
        switch (day % 10) {
            case 1:  return day + "st";
            case 2:  return day + "nd";
            case 3:  return day + "rd";
            default: return day + "th";
        }
    }

}
