package com.mediamate.settlement.request;

import com.mediamate.YearMonthResult;
import com.mediamate.meter.MeterType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeterRequest {
    private Long imageId;
    private MeterType meterType;
    private Long flatId;
    private Double meterValue;
    private YearMonthResult yearMonthResult;


    public boolean isEmpty() {
        return (imageId == null || imageId == 0) &&
                (meterType == null) &&
                (flatId == null || flatId == 0) &&
                (meterValue == null || meterValue == 0.0);
    }
}
