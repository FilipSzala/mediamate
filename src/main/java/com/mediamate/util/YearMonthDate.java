package com.mediamate.util;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class YearMonthDate {
    public int year;
    public int month;


    public YearMonthDate() {
    }

    public YearMonthDate(int year, int month) {
        this.year = year;
        this.month = month;
    }
    public LocalDate toLocalDate() {
        return LocalDate.of(year, month, 1);
    }

}