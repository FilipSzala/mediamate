package com.mediamate.cost.mediaCost;

import com.mediamate.cost.Cost;
import com.mediamate.cost.CostRepository;
import com.mediamate.cost.additionalCost.AdditionalCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

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

    public List <AdditionalCost> findAdditionalCostByRealEstateIdInCurrentMonth(Long realEstateId,LocalDate localDate){
        LocalDate firstDay = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDay =  localDate.with(TemporalAdjusters.lastDayOfMonth());
        List <AdditionalCost> additionalCosts =  costRepository.findAdditionalCostByRealEstateIdAndCostTypeInCurrentMonth(realEstateId,firstDay,lastDay);
        return additionalCosts;
    }
}
