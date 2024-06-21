package com.mediamate.model.additionalCost;

import com.mediamate.model.Cost;
import com.mediamate.model.realestate.RealEstate;
import com.mediamate.model.realestate.RealEstateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class AdditionalCostService {
    @Autowired
    AdditionalCostRepository additionalCostRepository;
    @Autowired
    HttpSession httpSession;

    RealEstateService realEstateService;

    public AdditionalCostService(RealEstateService realEstateService) {
        this.realEstateService = realEstateService;
    }

    @Transactional
    public void createAdditionalCost(AdditionalCost additionalCost){
        Cost cost = additionalCost;
        Long realEstateId =(Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate =realEstateService.findById(realEstateId).orElseThrow();
        realEstate.addCost(cost);
        realEstateService.updateRealEstate(realEstate);
    }
}
