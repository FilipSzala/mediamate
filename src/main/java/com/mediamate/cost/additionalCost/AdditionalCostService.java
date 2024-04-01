package com.mediamate.cost.additionalCost;

import com.mediamate.cost.Cost;
import com.mediamate.cost.CostService;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class AdditionalCostService {
    @Autowired
    AdditionalCostRepository additionalCostRepository;
    @Autowired
    HttpSession httpSession;

    RealEstateService realEstateService;
    CostService costService;

    public AdditionalCostService(RealEstateService realEstateService, CostService costService) {
        this.costService = costService;
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
