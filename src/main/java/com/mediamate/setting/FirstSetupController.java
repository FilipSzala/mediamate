package com.mediamate.setting;

import com.mediamate.owner.OwnerService;
import com.mediamate.realestate.RealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("settings")
public class FirstSetupController {
    OwnerService ownerService;
    RealEstateService realEstateService;

    @Autowired
    public FirstSetupController(OwnerService ownerService,RealEstateService realEstateService) {
        this.ownerService = ownerService;
        this.realEstateService = realEstateService;
    }

    @PostMapping("/first-setup")
    public void createOwner (@RequestBody OwnerRequest ownerRequest){
        ownerService.createOwner(ownerRequest);
    }
    @PatchMapping("/real-estates")
    public void setupRealEstates (@RequestBody List<RealEstateRequest> realEstateRequests){
        realEstateService.setupRealEstates(realEstateRequests);
    }
}
