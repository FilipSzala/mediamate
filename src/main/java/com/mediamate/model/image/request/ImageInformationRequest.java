package com.mediamate.model.photo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mediamate.model.meter.MeterType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PhotoInformationRequest {
    private MeterType meterType;
    private Long flatId;
    private double meterValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate yearMonthDate;

    public PhotoInformationRequest(MeterType meterType, Long flatId, double meterValue, LocalDate yearMonthDate) {
        this.meterType = meterType;
        this.flatId = flatId;
        this.meterValue = meterValue;
        this.yearMonthDate = yearMonthDate;
    }
}
