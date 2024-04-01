package com.mediamate.user;

import com.mediamate.register.token.Token;
import com.mediamate.user.role.UserRole;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity


public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String email;
    private String password;
    private Boolean enabled = false;
    private Boolean locked = false;
    @OneToOne (cascade = {CascadeType.PERSIST,CascadeType.MERGE},
                fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_role_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey (
                    name = "user_role_fk"
            )
    )
    private UserRole userRole;
    private String role;
    @OneToMany(
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            mappedBy = "user"
    )
    private List<Token> tokens = new ArrayList<>();

    public User() {
    }

    public User(String email, String password,String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void addToken(Token token) {
        if(!this.tokens.contains(token)){
          this.tokens.add(token);
          token.setUser(this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.role);
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
