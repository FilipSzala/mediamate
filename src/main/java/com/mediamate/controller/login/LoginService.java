package com.mediamate.controller.login;

import com.mediamate.config.security.SecurityService;
import com.mediamate.model.user.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class LoginService {
    SecurityService securityService;
    UserService userService;

    @Autowired
    public LoginService(SecurityService securityService,UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }
}
