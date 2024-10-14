package com.mediamate.dto.dashboard.components.table;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class TableWaterDto {
    private Long flatId;
    private double coldWaterMeterReadingPerFlat;
    private double warmWaterMeterReadingPerFlat;
    private BigDecimal usageColdAndWarmWater;
    private BigDecimal usageWaterWithSewage;
    private BigDecimal sumInZl;
    private BigDecimal administrationWaterPerFlat;
    private BigDecimal administrationWaterPerRealEstate;
}
