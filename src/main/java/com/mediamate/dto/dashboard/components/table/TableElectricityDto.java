package com.mediamate.dto.dashboard.components.table;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;


@Builder
@Getter
public class TableElectricityDto {
    private Long flatId;
    private BigDecimal meterReadingPerFlat;
    private BigDecimal usageWithoutAdministrationPerFlat;
    private BigDecimal usageIncludingAdministrationPerFlat;
    private BigDecimal meterReadingPerAdministration;
    private BigDecimal usagePerAdministration;
    private BigDecimal priceAdministration;
    private BigDecimal sum;
}
