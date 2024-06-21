package com.mediamate.model.cost.media_summary.detaile_summary;

import com.mediamate.model.cost.additionalCost.AdditionalCost;
import com.mediamate.model.cost.additionalCost.ChargeType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component

public class AdditionalCostSum {
    private int renterCount;
    private int flatCount;
    public double countTotalAdditionalCost(List<AdditionalCost> additionalCosts, int renterCount, int flatCount) {
        this.flatCount = flatCount;
        this.renterCount = renterCount;
        double totalCost = 0;
        for (AdditionalCost cost : additionalCosts) {
            totalCost = totalCost +  countSingleCost(cost);
        }
        return totalCost;
    }

    private double countSingleCost (AdditionalCost cost){
        if (cost.getChargeType().equals(ChargeType.FLAT)){
            return cost.getPrice()/(cost.getTimePeriod().getValue())/flatCount;
        }
        else {
            return cost.getPrice()*renterCount;
        }
    }
}
