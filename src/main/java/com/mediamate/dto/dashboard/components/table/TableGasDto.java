package com.mediamate.dto.dashboard.components.table;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class TableGasDto {
    private Long flatId;
    private BigDecimal meterReading;
    private BigDecimal usageInGj;
    private BigDecimal usageInM3;
    private BigDecimal sumInZl;
}
