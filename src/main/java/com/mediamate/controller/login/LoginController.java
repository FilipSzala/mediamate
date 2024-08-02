package com.mediamate.controller.login;


import com.mediamate.config.security.SecurityConfigure;
import com.mediamate.config.security.SecurityService;
import com.mediamate.model.user.User;
import com.mediamate.model.user.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@Hidden
@RestController
public class LoginController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityConfigure securityConfigure;

    private LoginService loginService;
    private SecurityService securityService;
    private UserService userService;
    @Autowired
    public LoginController(LoginService loginService, SecurityService securityService, UserService userService) {
        this.loginService = loginService;
        this.securityService = securityService;
        this.userService = userService;
    }


    @GetMapping ("login/redirect")
    public ResponseEntity<String> loginRedirect (){
            User user = securityService.findUserBySession();
            if (userService.isOwnerSetup(user)){
               return new ResponseEntity<String>("Choose realestate", HttpStatus.OK);
            }
                return new ResponseEntity<String>("Profile info", HttpStatus.OK);
    }
    }

