package com.mediamate.controller.profile_info;

import com.mediamate.controller.profile_info.request.InitialRequest;
import com.mediamate.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("first-setup")
public class ProfileInfoController {
    private UserService userService;

    @Autowired
    public ProfileInfoController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/owners")
    public void setupUserWithOwnerRole(@RequestBody InitialRequest initialRequest){
        userService.setupUser(initialRequest);
    }}

