package com.mediamate.summary.detaile_summary;

import com.mediamate.cost.additionalCost.AdditionalCost;
import org.springframework.stereotype.Service;

@Service
public class AdditionalCostSummaryService {
    public void createAdditionalCostSummary(AdditionalCost additionalCost){

        AdditionalCostSummary.builder()
                .name()
                .information()
                .priceForMonth()
    }
}
