package com.mediamate.cost.mediaCost;

import com.mediamate.cost.Cost;
import com.mediamate.cost.CostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaCostService {
    @Autowired
    MediaCostRepository mediaCostRepository;

    CostService costService;
    @Autowired
    HttpSession httpSession;
    @Autowired
    public MediaCostService(CostService costService) {
        this.costService = costService;
    }

    public void createMedia(MediaCost mediaCost){
        Long realEstateId =(Long) httpSession.getAttribute("chosenRealEstateId");
        Cost cost = new Cost(mediaCost);
        cost.setRealEstateId(realEstateId);
        costService.createPrice(cost);
    }
}
