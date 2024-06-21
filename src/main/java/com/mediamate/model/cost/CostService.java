package com.mediamate.model.cost.media_cost;

import com.mediamate.model.cost.CostRepository;
import com.mediamate.model.cost.additionalCost.AdditionalCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class CostService {
    @Autowired
    private CostRepository costRepository;

    public MediaCost findMediaCostByRealEstateIdInCurrentMonth(Long realEstateId,LocalDate localDate){
        LocalDate firstDay = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDay =  localDate.with(TemporalAdjusters.lastDayOfMonth());
        MediaCost mediaCost = (MediaCost) costRepository.findMediaCostByRealEstateIdAndCostTypeInCurrentMonth(realEstateId,firstDay,lastDay).orElseThrow();
        return mediaCost;
    }

    public List <AdditionalCost> findAdditionalCostByRealEstateIdInCurrentMonth(Long realEstateId, LocalDate localDate){
        LocalDate firstDay = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDay =  localDate.with(TemporalAdjusters.lastDayOfMonth());
        List <AdditionalCost> additionalCosts =  costRepository.findAdditionalCostByRealEstateIdAndCostTypeInCurrentMonth(realEstateId,firstDay,lastDay);
        return additionalCosts;
    }
}
