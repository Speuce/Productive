package com.productive6.productive.objects.tuples;

import androidx.room.ColumnInfo;

import java.time.LocalDate;

/**
 * Tuple for representing Day:Integer
 */
public class DayIntTuple {

    @ColumnInfo(name = "day")
    private LocalDate date;

    @ColumnInfo(name = "number")
    private Integer number;

    public DayIntTuple(LocalDate date, Integer number) {
        this.date = date;
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
