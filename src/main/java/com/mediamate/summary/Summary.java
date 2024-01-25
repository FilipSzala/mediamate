package com.mediamate.summary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Summary {
    private String rentersName;
    private BigDecimal waterPrice;
    private BigDecimal electricityPrice;
    private BigDecimal gasConsumption;
    private BigDecimal gasConsumptionInPercent;
    private BigDecimal gasPrice;
    private BigDecimal additionalPrice;
    private BigDecimal sum;
}