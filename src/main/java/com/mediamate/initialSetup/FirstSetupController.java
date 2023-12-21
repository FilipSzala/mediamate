package com.mediamate.initialSetup;

import com.mediamate.flat.FlatService;
import com.mediamate.owner.OwnerService;
import com.mediamate.realestate.RealEstateService;
import com.mediamate.initialSetup.request.FlatRequest;
import com.mediamate.initialSetup.request.OwnerRequest;
import com.mediamate.initialSetup.request.RealEstateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("first-setup")
public class FirstSetupController {
    OwnerService ownerService;
    RealEstateService realEstateService;
    FlatService flatService;
    @Autowired
    public FirstSetupController(OwnerService ownerService,RealEstateService realEstateService,FlatService flatService) {
        this.ownerService = ownerService;
        this.realEstateService = realEstateService;
        this.flatService = flatService;
    }

    @PostMapping("/owner")
    public void createOwner (@RequestBody OwnerRequest ownerRequest){

        ownerService.createOwner(ownerRequest);
    }
    @PatchMapping("/real-estates")
    public void setupRealEstates (@RequestBody List<RealEstateRequest> realEstateRequests){
        realEstateService.setupRealEstates(realEstateRequests);
    }
    @PatchMapping("/flats/{realEstateId}")
    public void setupFlats (@PathVariable Long realEstateId,@RequestBody List<FlatRequest> flatRequests){
        flatService.setupFlats(realEstateId,flatRequests);
    }}

