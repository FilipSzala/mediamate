package com.mediamate.summary.detaile_summary;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class GasSummary {
    private Double gasPrice;
    private BigDecimal gasTotalPriceForFlat;
    private BigDecimal gasTotalPriceForRealEstate;
    private Double gasMeterValueInFlat;
    private Double gasMeterValueInRealEstate;
    private BigDecimal gasConsumptionByFlatInGJ;
    private BigDecimal gasConsumptionByFlatInM3;
    private BigDecimal gasConsumptionByFlatInPercent;
    private BigDecimal gasConsumptionByRealEstateInM3;
}
