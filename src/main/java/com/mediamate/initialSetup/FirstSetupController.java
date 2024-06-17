package com.mediamate.initialSetup;

import com.mediamate.flat.FlatService;
import com.mediamate.initialSetup.request.InitialRequest;
import com.mediamate.realestate.RealEstateService;
import com.mediamate.security.SecurityService;
import com.mediamate.user.role.owner.OwnerRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("first-setup")
public class FirstSetupController {
    OwnerRoleService ownerRoleService;
    RealEstateService realEstateService;
    FlatService flatService;

    SecurityService securityService;
    @Autowired
    public FirstSetupController(OwnerRoleService ownerRoleService, RealEstateService realEstateService, FlatService flatService, SecurityService securityService) {
        this.ownerRoleService = ownerRoleService;
        this.realEstateService = realEstateService;
        this.flatService = flatService;
        this.securityService = securityService;
    }

    @PutMapping("/owners")
    public void uploadUserWithOwnerRole(@RequestBody InitialRequest initialRequest){
        ownerRoleService.uploadUser(initialRequest);
    }}

