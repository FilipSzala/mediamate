package com.mediamate.login;

import com.mediamate.user.role.owner.Owner;
import com.mediamate.user.role.owner.OwnerService;
import com.mediamate.security.SecurityService;
import com.mediamate.user.User;
import com.mediamate.user.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class LoginService {
    SecurityService securityService;
    UserService userService;
    OwnerService ownerService;

    @Autowired
    public LoginService(SecurityService securityService,UserService userService, OwnerService ownerService) {
        this.securityService = securityService;
        this.userService = userService;
        this.ownerService = ownerService;
    }

    public String redirect() {
        User user = securityService.findUserBySession();
        if (!userService.isOwnerRole(user)){
            //Todo: Redirect to main interface (it is user or admin)
            return "Main interface";
        }
        Owner owner = (Owner)user.getUserRole();
        if (ownerService.hasRealEstate(owner)){
            //Todo: Redirect to choose RealEstate after to main interface
            return "/choose/realestate/";
        }
        //Todo: Redirect to First Setup Controller - method create Owner
        return "First Setup Controller";
    }

}
