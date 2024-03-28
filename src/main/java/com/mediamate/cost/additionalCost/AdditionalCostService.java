package com.mediamate.cost.additionalCost;

import com.mediamate.cost.Cost;
import com.mediamate.cost.CostService;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void createAdditionalCost(AdditionalCost additionalCost){
       Long priceId = getPriceIdInCurrentMoth();
       additionalCost.setId(priceId);
        additionalCostRepository.save(additionalCost);
    }
    private Long getPriceIdInCurrentMoth(){
        Long realEstateId =(Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate = realEstateService.findById(realEstateId).get();
        List<Cost> costs = realEstate.getCosts();
        Cost cost = costService.getPriceInCurrentMonth(costs);
        Long priceId = cost.getId();
       return priceId;
    }


}
