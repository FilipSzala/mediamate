package com.mediamate.summary.detaile_summary;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Builder
@Getter
@Setter

public class WaterSummary {
    private Double price;
    private Double meterValueForHotWater;
    private Double meterValueForColdWater;
    private BigDecimal consumptionByFlat;
    private BigDecimal wasteWaterConsumptionByFlat;
    private BigDecimal totalPriceForFlat;
}
