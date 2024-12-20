package com.mediamate.controller.register;

import com.mediamate.model.real_estate.RealEstate;
import com.mediamate.model.real_estate.RealEstateService;
import com.mediamate.model.renter.Renter;
import com.mediamate.model.renter.RenterService;
import com.mediamate.model.token.Token;
import com.mediamate.controller.register.email.EmailSenderService;
import com.mediamate.model.token.TokenService;
import com.mediamate.model.user.User;
import com.mediamate.model.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RegistrationService {
    private UserService userService;
    private TokenService tokenService;
    private EmailSenderService emailSenderService;
    private RealEstateService realEstateService;
    private String emailSubject = "Verification";

    private String emailInformationHeader = """
        Dear User,

        Thank you for registering. To complete the verification process, please enter the following token in the appropriate field on our web application:

        Your verification token:\n\n""";

    private String emailInformationFooter = """
        \n\nIf you did not initiate this request, you can safely ignore this email.

        Thank you for your cooperation!
        """;

    private RenterService renterService;


    @Autowired
    public RegistrationService(RealEstateService realEstateService,UserService userService, TokenService tokenService, EmailSenderService emailSenderService,RenterService renterService) {
        this.realEstateService = realEstateService;
        this.userService = userService;
        this.tokenService = tokenService;
        this.emailSenderService = emailSenderService;
        this.renterService = renterService;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> registerOwner(OwnerRegistrationRequest ownerRegistrationRequest) throws Exception {
        try {
            Boolean userExists = emailVerify(ownerRegistrationRequest.getEmail());
            if (userExists) {
                throw new IllegalStateException("Email already taken");
            }
            Boolean emailValided = emailValid(ownerRegistrationRequest.getEmail());
            if (!emailValided) {
                throw new IllegalStateException("Invalid email");
            }
            User user = new User(
                    ownerRegistrationRequest.getEmail(),
                    ownerRegistrationRequest.getPassword(),
                    "OWNER");

            Token token = tokenService.createToken();
            user.addToken(token);

            userService.createUser(user);
            sendEmailWithToken(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<String> registerUser (HttpSession httpSession, UserRegistrationRequest userRegistrationRequest) throws  Exception{
        try {
            if(realEstateService.findRealEstateByHttpSession(httpSession)==null){
                throw new IllegalStateException("You must to be login if you want to create account for user");
            }
            RealEstate realEstate = realEstateService.findRealEstateByHttpSession(httpSession);
            Renter renter = renterService.findRenterById(userRegistrationRequest.renterId);
            User user = new User(
                    userRegistrationRequest.getName(),
                    userRegistrationRequest.getPassword(),
                    "USER",
                    renter
            );
            user.addRealEstate(realEstate);
            user.setEnabled(true);
            userService.createUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            throw e;
        }
    }
    public void sendEmailWithToken(User user) throws Exception {
        try {
            String tokenKey = tokenService.findKeyTokenByEmail(user.getEmail());
            emailSenderService.sendEmail(
                    user.getEmail(),
                    emailSubject,
                    emailInformationHeader + tokenKey + emailInformationFooter);
        }
        catch (Exception e){
            throw new Exception(e.getMessage() + " Wrong email");
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

    @Transactional
    public ResponseEntity<String> confirmOwner(String tokenKey) {
        try {
            tokenService.confirmToken(tokenKey);
            userService.enableUser(tokenKey);
            return new ResponseEntity<>("Account confirmed",HttpStatus.OK);
        } catch (IllegalStateException e) {
           throw e;
        }
    }
}

