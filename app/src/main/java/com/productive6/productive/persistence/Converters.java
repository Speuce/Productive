package com.productive6.productive.persistence;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * Defines type converters for the room library to use.
 */
public class Converters {

    @TypeConverter
    public static LocalDate fromTimestamp(Long value) {
        return value == null ? null : Instant.ofEpochSecond(value).atZone(ZoneOffset.UTC).toLocalDate();
    }

    @TypeConverter
    public static Long dateToTimestamp(LocalDate date) {
        return date == null ? null : date.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
    }

    @TypeConverter
    public static LocalDateTime dateTimefromTimestamp(Long value) {
        return value == null ? null : Instant.ofEpochMilli(value).atZone(ZoneOffset.UTC).toLocalDateTime();
    }

    @TypeConverter
    public static Long dateTimeToTimestamp(LocalDateTime date) {
        return date == null ? null : date.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
    }

}
