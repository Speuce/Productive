package com.productive6.productive.persistence.room.adapters;

import androidx.room.TypeConverter;

import com.productive6.productive.objects.enums.Difficulty;
import com.productive6.productive.objects.enums.Priority;
import com.productive6.productive.objects.enums.Category;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Defines type converters for the room library to use.
 */
public class Converters {

    @TypeConverter
    public static LocalDate fromTimestamp(Long value) {
        return value == null ? null : LocalDate.ofEpochDay(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(LocalDate date) {
        return date == null ? null : date.toEpochDay();
    }

    @TypeConverter
    public static LocalDateTime dateTimefromTimestamp(Long value) {
        return value == null ? null : Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @TypeConverter
    public static Long dateTimeToTimestamp(LocalDateTime date) {
        return date == null ? null : date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @TypeConverter
    public static Difficulty difficultyFromInt(int value) {
        return Difficulty.values()[value];
    }

    @TypeConverter
    public static Category categoryFromInt(int value) {
        return Category.values()[value];
    }

    @TypeConverter
    public static int intFromCategory(Category c) {
        return c.ordinal();
    }

    @TypeConverter
    public static int intFromDifficulty(Difficulty d) {
        return d.ordinal();
    }

    @TypeConverter
    public static Priority priorityFromInt(int value) {
        return Priority.values()[value];
    }

    @TypeConverter
    public static int intFromPriority(Priority p) {
        return p.ordinal();
    }
}
