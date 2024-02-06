package com.mediamate.summary.detaile_summary;

import com.mediamate.cost.additionalCost.AdditionalCost;
import com.mediamate.cost.additionalCost.ChargeType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdditionalCostSummaryService {
    private BigDecimal flatCount=BigDecimal.ZERO;
    public List<AdditionalCostSummary> createAdditionalCostsSummary(List<AdditionalCost> additionalCosts,int flatAmount) {
        flatCount = new BigDecimal(flatAmount);

        List<AdditionalCostSummary> additionalCostsSummary = additionalCosts.stream()
                .map(additionalCost -> createAdditionCostSummary(additionalCost))
                .collect(Collectors.toList());
        return additionalCostsSummary;
    }
    public AdditionalCostSummary createAdditionCostSummary (AdditionalCost additionalCost){
       return AdditionalCostSummary.builder()
                .name(additionalCost.getName())
                .information(additionalCost.getInformation())
                .priceForMonth(countPriceForMonth(additionalCost))
                .chargeType(additionalCost.getChargeType())
               .build();
    }

    private BigDecimal countPriceForMonth(AdditionalCost additionalCost){

        BigDecimal billingTimePeriod = new BigDecimal(additionalCost.getTimePeriod().getValue());
        BigDecimal additionalCostAmount = new BigDecimal(additionalCost.getCost());
        if(additionalCost.getChargeType().equals(ChargeType.FLAT)){
            BigDecimal priceForMonth = additionalCostAmount
                    .divide(billingTimePeriod, 2, RoundingMode.UP)
                    .divide(flatCount, 2, RoundingMode.UP);
            return priceForMonth;
        }
        else{
            BigDecimal priceForMonth =  additionalCostAmount.divide(billingTimePeriod,2,RoundingMode.UP);
            return priceForMonth;
    }
}}
