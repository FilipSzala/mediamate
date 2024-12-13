package com.mediamate.model.image.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mediamate.model.meter.MeterType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ImageInformationRequest {
    private MeterType meterType;
    private Long flatId;
    private double meterValue;
    private String flatName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate yearMonthDate;

    public ImageInformationRequest(MeterType meterType, Long flatId, double meterValue, LocalDate yearMonthDate,String flatName) {
        this.meterType = meterType;
        this.flatId = flatId;
        this.meterValue = meterValue;
        this.yearMonthDate = yearMonthDate;
        this.flatName = flatName;
    }
}
