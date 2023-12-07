package com.mediamate.register;

import com.mediamate.user.User;
import com.mediamate.user.UserRole;
import com.mediamate.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    UserService userService;

    @Autowired
    public RegistrationService(UserService userService) {
        this.userService = userService;
    }

    public void register(RegistrationRequest registrationRequest){
        //Todo : verification e-mail and email validator
        User user = new User(
                registrationRequest.getEmail(),
                registrationRequest.getPassword(),
                UserRole.FLAT_OWNER);

        userService.createUser(user);
    }
}
