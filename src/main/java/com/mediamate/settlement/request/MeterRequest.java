package com.mediamate.settlement.request;

import com.mediamate.meter.MeterType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MeterRequest {
    Long imageId;
    MeterType meterType;
    Long flatId;
    Double meterValue;
}
