package com.mediamate.realestate;

import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/real-estate")
public class RealEstateController {
    RealEstateService realEstateService;
    @Autowired
    public RealEstateController(RealEstateService realEstateService) {
        this.realEstateService = realEstateService;
    }

    @PostMapping("/{id}")
    public void addRealEstate(@PathVariable ("id") Long ownerId, @RequestBody RealEstate realEstate){
        realEstateService.addRealEstate(ownerId,realEstate);
    }
}
