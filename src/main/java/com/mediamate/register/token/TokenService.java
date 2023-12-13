package com.mediamate.register.token;

import com.mediamate.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {
    @Autowired
    TokenRepository tokenRepository;

    public void createToken(User user){
        String generatedToken = generateToken();
        Token tokenObject = new Token(
                generatedToken,
                LocalDate.now(),
                LocalDateTime.now().plusMinutes(5),
                user
        );
        tokenRepository.save(tokenObject);
    }
    public Optional <Token> findByKey (String key){
       return tokenRepository.findByTokenKey(key);
    }

    private String generateToken (){
        return UUID.randomUUID().toString();
    }

}
