package com.mediamate.login;

import com.mediamate.user.role.owner.Owner;
import com.mediamate.user.role.owner.OwnerRoleService;
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
    OwnerRoleService ownerRoleService;

    @Autowired
    public LoginService(SecurityService securityService,UserService userService, OwnerRoleService ownerRoleService) {
        this.securityService = securityService;
        this.userService = userService;
        this.ownerRoleService = ownerRoleService;
    }
}
