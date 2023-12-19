package com.mediamate.register;

import com.mediamate.register.email.EmailSenderService;
import com.mediamate.register.token.TokenService;
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
    TokenService tokenService;
    EmailSenderService emailSenderService;
    String emailSubject = "Verification link";

    //Todo: Remember to change link after develop app on external server!!!
    String linkVerification = "Link for verification account : http://localhost:8080/register/confirm?tokenKey=";


    @Autowired
    public RegistrationService(UserService userService, TokenService tokenService,EmailSenderService emailSenderService) {

        this.userService = userService;
        this.tokenService = tokenService;
        this.emailSenderService = emailSenderService;
    }

    public String register(RegistrationRequest registrationRequest) {
        try {
            Boolean userExists = emailVerify(registrationRequest.getEmail());
            if (userExists) {
                throw new IllegalStateException("Email already taken");
            }
            Boolean emailValided = emailValid(registrationRequest.getEmail());
            if (!emailValided) {
                throw new IllegalStateException("Invalid email");
            }
            User user = new User(
                    registrationRequest.getEmail(),
                    registrationRequest.getPassword(),
                    UserRole.FLAT_OWNER);

            userService.createUser(user);

            String tokenKey = tokenService.findKeyTokenByEmail(user.getEmail());
            emailSenderService.sendEmail(
                    user.getEmail(),
                    emailSubject,
                    linkVerification+tokenKey
            );

            return HttpStatus.OK.name();
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    private Boolean emailVerify(String email) {
        Optional user = userService.findByEmail(email);
        return user.isPresent() ? true : false;
    }

    private Boolean emailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";
        return email.matches(emailRegex) ? true : false;
    }

    public String confirmUser(String tokenKey) {
        try {
            tokenService.confirmToken(tokenKey);
            userService.enableUser(tokenKey);
            return "Account confirmed";
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

}

