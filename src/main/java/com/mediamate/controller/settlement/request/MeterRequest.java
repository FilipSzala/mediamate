package com.mediamate.controller.settlement.request;

import com.mediamate.util.YearMonthDate;
import com.mediamate.model.meter.MeterOwnership;
import com.mediamate.model.meter.MeterType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class MeterRequest {
    private Long imageId;
    private MeterType meterType;
    private MeterOwnership meterOwnership;
    private Long flatId;
    private Double meterValue;
    private YearMonthDate yearMonthDate = new YearMonthDate();

    public MeterRequest() {
    }

    public MeterRequest(Long imageId, MeterType meterType, MeterOwnership meterOwnership, Long flatId, Double meterValue, YearMonthDate yearMonthDate) {
        this.imageId = imageId;
        this.meterType = meterType;
        this.meterOwnership = meterOwnership;
        this.flatId = flatId;
        this.meterValue = meterValue;
        this.yearMonthDate = yearMonthDate;
    }

    public boolean isEmpty() {
        return (imageId == null || imageId == 0) &&
                (meterType == null) &&
                (flatId == null || flatId == 0) &&
                (meterValue == null || meterValue == 0.0);
    }

    public void setYearMonthDateByLocaldate(LocalDate localDate){
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        yearMonthDate.month = month;
        yearMonthDate.year = year;
    }

}
