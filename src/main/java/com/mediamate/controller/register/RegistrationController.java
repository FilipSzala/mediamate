package com.mediamate.register;

import com.mediamate.flat.Flat;
import com.mediamate.realestate.RealEstate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")

public class RegistrationController {

    RegistrationService registrationService;
    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping()
    public String registerForOwner(@RequestBody RegistrationRequest registrationRequest){
        return registrationService.register(registrationRequest);
    }

    @GetMapping (path = "confirm")
    public String confirmUser (@RequestParam String tokenKey){
        return registrationService.confirmUser(tokenKey);
    }
    }
