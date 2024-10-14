package com.mediamate.controller.login;


import com.mediamate.model.user.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class LoginService {
    UserService userService;

    @Autowired
    public LoginService(UserService userService) {
        this.userService = userService;
    }
}
