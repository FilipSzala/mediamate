package com.mediamate.cost.mediaCost;

import com.mediamate.cost.Cost;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MediaCostService {
    RealEstateService realEstateService;
    @Autowired
    HttpSession httpSession;


    @Autowired
    public MediaCostService(RealEstateService realEstateService) {
        this.realEstateService =realEstateService;
    }

    @Transactional
    public void createMedia(MediaCost mediaCost){
        Cost cost = mediaCost;
        Long realEstateId =(Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate =realEstateService.findById(realEstateId).orElseThrow();
        realEstate.addCost(cost);
        realEstateService.updateRealEstate(realEstate);
    }
}
