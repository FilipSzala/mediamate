package com.mediamate.initialSetup;

import com.mediamate.flat.Flat;
import com.mediamate.flat.FlatService;
import com.mediamate.realestate.RealEstateDto;
import com.mediamate.user.role.owner.OwnerService;
import com.mediamate.realestate.RealEstateService;
import com.mediamate.initialSetup.request.FlatRequest;
import com.mediamate.initialSetup.request.OwnerRequest;
import com.mediamate.initialSetup.request.RealEstateRequest;
import com.mediamate.security.SecurityService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("first-setup")
public class FirstSetupController {
    OwnerService ownerService;
    RealEstateService realEstateService;
    FlatService flatService;

    SecurityService securityService;
    @Autowired
    public FirstSetupController(OwnerService ownerService,RealEstateService realEstateService,FlatService flatService, SecurityService securityService) {
        this.ownerService = ownerService;
        this.realEstateService = realEstateService;
        this.flatService = flatService;
        this.securityService = securityService;
    }
    // IMPORTANT!!!
    // All of these methods are useful only for logged-in users and return objects belonging to the user. For example,
    // method getRealEstates in this class doesn't return all real estates in database, but only the real estates logged-in user.
    @PostMapping("/owner")
    public void createOwner (@RequestBody OwnerRequest ownerRequest){
        ownerService.createOwner(ownerRequest);
    }
    @PatchMapping("/real-estates")
    public void setupRealEstates (@RequestBody List<RealEstateRequest> realEstateRequests){
        realEstateService.setupRealEstates(realEstateRequests);
    }
    @GetMapping ("/real-estates")
    public List<RealEstateDto> getRealEstatesLoginUser(){
        List <RealEstateDto> realEstateDtos = realEstateService.findAllByLogInUser();
        return  realEstateDtos;
    }


    @GetMapping("/flats/{realEstateId}")
    public List<Flat> getFlatsByRealEstateId (@PathVariable Long realEstateId){
    return flatService.findFlatsByRealEstateId(realEstateId);
    }
    @PatchMapping("/flats/{realEstateId}")
    public void setupFlats (@PathVariable Long realEstateId,@RequestBody List<FlatRequest> flatRequests){
        flatService.setupFlats(realEstateId,flatRequests);
    }}

