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

    public void createToken(User user) {
        String generatedToken = generateToken();
        Token tokenObject = new Token(
                generatedToken,
                LocalDate.now(),
                LocalDateTime.now().plusMinutes(5),
                user
        );
        tokenRepository.save(tokenObject);
    }

    public Optional<Token> findByKey(String key) {
        return tokenRepository.findByTokenKey(key);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void confirmToken(String tokenKey) {
        if (!isTokenExists(tokenKey)) {
            throw new IllegalStateException("Incorrect token");
        }
        if (isTokenConfirmed(tokenKey)) {
            throw new IllegalStateException("Token already confirmed");
        }
        if (isTimeExpired(tokenKey)) {
            throw new IllegalStateException("Time for account confirmation has expired");
        }
        Token databaseToken = tokenRepository.findByTokenKey(tokenKey).get();
        Token modifyToken = tokenRepository.findByTokenKey(tokenKey).get();
        modifyToken.setConfirmedAt(LocalDateTime.now());
        updateTokenPartially(databaseToken, modifyToken);
    }


    public void updateTokenPartially(Token databaseToken, Token modifyToken) {
        databaseToken.setTokenKey(modifyToken.getTokenKey());
        databaseToken.setCreatedAt(modifyToken.getCreatedAt());
        databaseToken.setExpiredAt(modifyToken.getExpiredAt());
        databaseToken.setConfirmedAt(modifyToken.getConfirmedAt());
        databaseToken.setUser(modifyToken.getUser());
        tokenRepository.save(databaseToken);
    }

    private Boolean isTokenExists(String tokenKey) {
        return tokenRepository.findByTokenKey(tokenKey).isPresent() ? true : false;
    }

    private Boolean isTokenConfirmed(String tokenKey) {
        return tokenRepository.findByTokenKey(tokenKey).get().getConfirmedAt() != null ? true : false;
    }

    private Boolean isTimeExpired(String tokenKey) {
        Token token = tokenRepository.findByTokenKey(tokenKey).get();
        LocalDateTime expireDate = token.getExpiredAt();
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.isBefore(expireDate) ? false : true;
    }

    public Optional<Token> findTokenByEmail(String email) {
        return tokenRepository.findByUserEmail(email);
    }
    public String findKeyTokenByEmail(String email){
        Token token = findTokenByEmail(email).get();
        String tokenKey = token.getTokenKey();
        return tokenKey;
    }
}