package com.mediamate.summary;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@Builder
class MediaSummary{
    private LocalDate createdAt;
    private String rentersName;
    private Double electricityPrice;
    private Double electricityMeterValueInFlat;
    private Double electricityMeterValueForAdministration;
    private BigDecimal adminElectricityConsumptionPerFlat;
    private BigDecimal electricityConsumptionByFlat;
    private BigDecimal electricityTotalPriceForFlat;
    private Double gasPrice;
    private BigDecimal gasTotalPriceForFlat;
    private BigDecimal gasTotalPriceForRealEstate;
    private Double gasMeterValueInFlat;
    private Double gasMeterValueInRealEstate;
    private BigDecimal gasConsumptionByFlatInGJ;
    private BigDecimal gasConsumptionByFlatInM3;
    private BigDecimal gasConsumptionByFlatInPercent;
    private BigDecimal gasConsumptionByRealEstateInM3;
    private Double waterPrice;
    private BigDecimal waterTotalPriceForFlat;
    private Double meterValueForHotWater;
    private Double meterValueForColdWater;
    private BigDecimal waterConsumptionByFlat;
    private BigDecimal wasteWaterConsumptionByFlat;
    private BigDecimal totalMediaSumByFlat;

    private BigDecimal additionalPrice;
}