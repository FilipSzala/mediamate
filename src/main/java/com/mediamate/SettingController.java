package com.mediamate;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settings")
public class SettingController {
    // This method can be used to set variables in Owner after first login or change the value of variables for an existing Owner.
    @PatchMapping
    public void editOwner(){

    }
}
