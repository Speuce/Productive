package com.productive6.productive.objects.tuples;

import androidx.room.ColumnInfo;

import java.time.LocalDate;

/**
 * Similar to {@link DayIntTuple}, but instead of internally representing time using LocalDate, keeps it
 * in epoch time
 */
public class EpochIntTuple {

    @ColumnInfo(name = "day")
    private long date;

    @ColumnInfo(name = "number")
    private Integer number;

    public EpochIntTuple(long date, Integer number) {
        this.date = date;
        this.number = number;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
