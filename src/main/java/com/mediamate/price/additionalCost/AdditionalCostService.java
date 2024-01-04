package com.mediamate.price.additionalCost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class AdditionalCostService {
    @Autowired
    AdditionalCostRepository additionalCostRepository;
    public void createAdditionalCost(AdditionalCost additionalCost,Long priceId){

        additionalCost.setPriceId(priceId);
        additionalCostRepository.save(additionalCost);
    }
}
