package com.mediamate.model.media_summary.detaile_summary;

import com.mediamate.model.cost.additionalCost.AdditionalCost;
import com.mediamate.model.cost.additionalCost.ChargeType;
import com.mediamate.model.media_summary.MediaSummary;
import com.mediamate.model.media_summary.additional_cost.MediaSummaryAdditionalCost;
import com.mediamate.model.media_summary.additional_cost.MediaSummaryAdditionalCostRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component

public class AdditionalCostSum {
    private int renterCount;
    private int flatCount;
    private List<MediaSummaryAdditionalCost> mediaSummaryAdditionalCosts;
    @Autowired
    private MediaSummaryAdditionalCostRepository mediaSummaryAdditionalCostRepository;
    private BigDecimal singleCost = new BigDecimal(BigInteger.ZERO);
    public List<MediaSummaryAdditionalCost> countTotalAdditionalCost(List<AdditionalCost> additionalCosts, int renterCount, int flatCount, MediaSummary mediaSummary) {
        this.flatCount = flatCount;
        this.renterCount = renterCount;
        this.mediaSummaryAdditionalCosts = new ArrayList<>();
        for (AdditionalCost cost : additionalCosts) {
            MediaSummaryAdditionalCost mediaSummaryAdditionalCost = new MediaSummaryAdditionalCost();
            singleCost = BigDecimal.valueOf(countSingleCost(cost));
            mediaSummaryAdditionalCost.setName(cost.getName());
            mediaSummaryAdditionalCost.setValue(singleCost);
            mediaSummaryAdditionalCosts.add(mediaSummaryAdditionalCost);
        }
        return mediaSummaryAdditionalCosts;
    }

    private double countSingleCost (AdditionalCost cost){
        if (cost.getChargeType().equals(ChargeType.FLAT)){
            return cost.getPrice()/(cost.getTimePeriod().doubleValue()+1)/flatCount;
        }
        else {
            return cost.getPrice()*renterCount;
        }
    }

    public List<MediaSummaryAdditionalCost> getMediaSummaryAdditionalCosts() {
        return mediaSummaryAdditionalCosts;
    }
}
