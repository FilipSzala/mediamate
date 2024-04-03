package com.mediamate.summary;

import com.mediamate.summary.detaile_summary.AdditionalCostSummary;
import com.mediamate.summary.detaile_summary.ElectricitySummary;
import com.mediamate.summary.detaile_summary.GasSummary;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Builder
class MediaSummary{
    private LocalDate createdAt;
    private String rentersName;
    private ElectricitySummary electricitySummary;
    private GasSummary gasSummary;
    private List<AdditionalCostSummary>additionalCostSummaries;
    private BigDecimal totalAdditionalCost;
    private BigDecimal totalMediaSumByFlat;
}