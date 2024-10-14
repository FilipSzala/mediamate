package com.mediamate.controller.login;


import com.mediamate.config.security.SecurityConfigure;
import com.mediamate.model.user.User;
import com.mediamate.model.user.UserService;
import com.mediamate.model.user.request.UserInfo;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpSession;
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
    private UserService userService;
    @Autowired
    public LoginController(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }


    @GetMapping ("login/redirect")
    public ResponseEntity<String> loginRedirect (){
            User user = userService.findUserBySession();
            if (userService.isOwnerSetup(user)){
               return new ResponseEntity<String>("Choose realestate", HttpStatus.OK);
            }
                return new ResponseEntity<String>("Profile info", HttpStatus.OK);
    }

    @GetMapping ("login/user-info")
    public UserInfo getUserInfo(HttpSession httpSession){
       User user = userService.findUserBySession();
        UserInfo userInfo = new UserInfo(user.getId(), user.getRole());
       return userInfo;
    }
    }

