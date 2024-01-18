package com.mediamate;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class YearMonthResult {
    public int year;
    public int month;

    public YearMonthResult(int year, int month) {
        this.year = year;
        this.month = month;
    }
    public LocalDate toLocalDate() {
        return LocalDate.of(year, month, 1);
    }
}
