package com.mediamate.controller.profile_info;

import com.mediamate.config.security.SecurityService;
import com.mediamate.controller.profile_info.request.InitialRequest;
import com.mediamate.model.flat.FlatService;
import com.mediamate.model.real_estate.RealEstateService;
import com.mediamate.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("first-setup")
public class ProfileInfoController {
    UserService userService;
    RealEstateService realEstateService;
    FlatService flatService;

    SecurityService securityService;
    @Autowired
    public ProfileInfoController(UserService userService, RealEstateService realEstateService, FlatService flatService, SecurityService securityService) {
        this.userService = userService;
        this.realEstateService = realEstateService;
        this.flatService = flatService;
        this.securityService = securityService;
    }

    @PutMapping("/owners")
    public void setupUserWithOwnerRole(@RequestBody InitialRequest initialRequest){
        userService.setupUser(initialRequest);
    }}

