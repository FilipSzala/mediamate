package com.mediamate.summary.detaile_summary;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Builder
@Getter
@Setter

public class ElectricitySummary  {
    private Double electricityPrice;
    private Double electricityMeterValueInFlat;
    private Double electricityMeterValueForAdministration;
    private BigDecimal adminElectricityConsumptionPerFlat;
    private BigDecimal electricityConsumptionByFlat;
    private BigDecimal electricityTotalPriceForFlat;
}
