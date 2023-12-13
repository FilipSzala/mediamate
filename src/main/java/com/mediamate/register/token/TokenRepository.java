package com.mediamate.register.token;

import com.mediamate.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByTokenKey (String tokenKey);

    Optional<Token> findByUserEmail(String email);
}
