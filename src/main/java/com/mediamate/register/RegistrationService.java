package com.mediamate.register;

import com.mediamate.user.User;
import com.mediamate.user.UserRole;
import com.mediamate.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService {
    UserService userService;

    @Autowired
    public RegistrationService(UserService userService) {
        this.userService = userService;
    }

    public String register(RegistrationRequest registrationRequest){
        try {
            Boolean userExists = emailVerify(registrationRequest.getEmail());
            if (userExists) {
                throw new IllegalStateException("email already taken");
            }
            Boolean emailValided = emailValid(registrationRequest.getEmail());
            if (!emailValided){
                throw  new IllegalStateException("invalid email");
            }
            User user = new User(
                    registrationRequest.getEmail(),
                    registrationRequest.getPassword(),
                    UserRole.FLAT_OWNER);

            userService.createUser(user);
            return HttpStatus.OK.name();
        }
     catch (IllegalStateException e){
                return e.getMessage();
            }
    }

    private Boolean emailVerify (String email) {
        Optional user = userService.findByEmail(email);
        return user.isPresent()? true:false;
    }

        private Boolean emailValid(String email){
            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";
            return email.matches(emailRegex)? true : false;
        }
    }

