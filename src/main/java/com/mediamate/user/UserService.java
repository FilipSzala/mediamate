package com.mediamate.user;

import com.mediamate.register.token.Token;
import com.mediamate.register.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private TokenService tokenService;
    @Autowired
    public UserService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public void createUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        tokenService.createToken(user);

    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void enableUser (String tokenKey){
        Optional <Token> token = tokenService.findByKey(tokenKey);
        User databaseUser=token.get().getUser();
        User modifyUser=token.get().getUser();
        modifyUser.setEnabled(true);
        updateUserPartially(databaseUser,modifyUser);
    }
    public void updateUserPartially(User databaseUser,User modifiedUser) {
        databaseUser.setFirstName(modifiedUser.getFirstName());
        databaseUser.setLastName(modifiedUser.getLastName());
        databaseUser.setEmail(modifiedUser.getEmail());
        databaseUser.setPassword(modifiedUser.getPassword());
        databaseUser.setEnabled(modifiedUser.getEnabled());
        databaseUser.setLocked(modifiedUser.getLocked());
        databaseUser.setUserRole(modifiedUser.getUserRole());
        userRepository.save(databaseUser);
    }
}