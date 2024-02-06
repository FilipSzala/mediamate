package com.mediamate.summary.detaile_summary;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter

public class ElectricitySummary  {
    private Double price;
    private Double meterValueInFlat;
    private Double meterValueForAdministration;
    private BigDecimal adminConsumptionPerFlat;
    private BigDecimal consumptionByFlat;
    private BigDecimal totalPriceForFlat;
}
