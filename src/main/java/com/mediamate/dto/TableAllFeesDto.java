package com.mediamate.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class TableAllFeesDto {
    private Long id;
    private LocalDate createdAt;
    private Long flatId;
    private BigDecimal totalElectricityCost;
    private BigDecimal totalGasCost;
    private BigDecimal totalWaterCost;
    private BigDecimal sewarageCost;
    private BigDecimal totalAdditionalCost;
    private BigDecimal totalAllMediaCost;
}
