package com.mediamate.summary.detaile_summary;

import com.mediamate.cost.additionalCost.ChargeType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Month;

@Builder
@Getter
@Setter

public class AdditionalCostSummary {
    private String name;
    private String information;
    private BigDecimal priceForMonth;
    private ChargeType chargeType;
}
