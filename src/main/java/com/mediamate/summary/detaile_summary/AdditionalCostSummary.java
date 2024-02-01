package com.mediamate.summary.detaile_summary;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Builder
@Getter
@Setter

public class AdditionalCostSummary {
    private String name;
    private String information;
    private BigDecimal priceForMonth;
}
