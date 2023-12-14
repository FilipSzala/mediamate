package com.mediamate.login;

import com.mediamate.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginCotroller {
    @GetMapping("/login/rediraction")
    public String redirect (){
        //Todo : rediraction depend on user role.
        Object check = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email =((User)check).getEmail();
        return email;
    }

}
