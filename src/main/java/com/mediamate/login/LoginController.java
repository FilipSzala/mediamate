package com.mediamate.login;


import com.mediamate.register.RegistrationRequest;
import com.mediamate.security.SecurityService;
import com.mediamate.security.config.SecurityConfigure;
import com.mediamate.user.User;
import com.mediamate.user.UserService;
import com.mediamate.user.role.owner.OwnerRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityConfigure securityConfigure;

    private LoginService loginService;
    private SecurityService securityService;
    private UserService userService;
    private OwnerRoleService ownerRoleService;
    @Autowired
    public LoginController(LoginService loginService, SecurityService securityService, UserService userService,OwnerRoleService ownerRoleService) {
        this.loginService = loginService;
        this.securityService = securityService;
        this.userService = userService;
        this.ownerRoleService = ownerRoleService;
    }



  /* @PostMapping("/login")

    public String login(@RequestBody RegistrationRequest request) {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);


            return "Login successful";
        } catch (AuthenticationException e) {
            return "Login failed: " + e.getMessage();
        }
    }*/
    @GetMapping ("login/redirect")
    public String loginRedirect (){
            User user = securityService.findUserBySession();
            if (userService.isUserRoleCreated(user)){
                //Todo: Redirect to main interface (it is user or admin)
                return "Dashboard";
            }
            if (ownerRoleService.hasRealEstate(user)){
                //Todo: Redirect to choose RealEstate after to main interface
                return "Choose realestate";
            }
            //Todo: Redirect to First Setup Controller - method create Owner
            return "Profile info";
        }
    }

