package com.mediamate.controller.register;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@Hidden
public class RegistrationController {

    RegistrationService registrationService;
    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping()
    public ResponseEntity<String> registerForOwner(@RequestBody OwnerRegistrationRequest ownerRegistrationRequest) throws Exception {
        return registrationService.registerOwner(ownerRegistrationRequest);
    }
    @PostMapping("/user")
    public ResponseEntity<String> registerForUser (@RequestBody UserRegistrationRequest userRegistrationRequest, HttpSession httpSession) throws Exception{
        return  registrationService.registerUser(httpSession,userRegistrationRequest);
    }

    @GetMapping (path = "confirm")
    public ResponseEntity<String> confirmOwner(@RequestParam String tokenKey){
        return registrationService.confirmOwner(tokenKey);
    }
    }