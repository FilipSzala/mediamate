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
}