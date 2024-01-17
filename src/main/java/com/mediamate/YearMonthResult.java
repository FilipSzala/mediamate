package com.mediamate;

import lombok.Getter;

@Getter
public class YearMonthResult {
    public int year;
    public int month;

    public YearMonthResult(int year, int month) {
        this.year = year;
        this.month = month;
    }
}
