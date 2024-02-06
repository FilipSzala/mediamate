package com.mediamate.summary.detaile_summary;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class GasSummary {
    private Double price;
    private BigDecimal totalPriceForFlat;
    private BigDecimal totalPriceForRealEstate;
    private Double meterValueInFlat;
    private Double meterValueInRealEstate;
    private BigDecimal consumptionByFlatInGJ;
    private BigDecimal consumptionByFlatInM3;
    private BigDecimal consumptionByFlatInPercent;
    private BigDecimal consumptionByRealEstateInM3;
}
