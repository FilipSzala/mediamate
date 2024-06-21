package com.mediamate.controller.register.token;

import com.mediamate.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String tokenKey;
    private LocalDate createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(
            name = "userId",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "token_user_fk"
            ))
    private User user;


    public Token(String tokenKey, LocalDate createdAt, LocalDateTime expiredAt) {
        this.tokenKey = tokenKey;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }
}
