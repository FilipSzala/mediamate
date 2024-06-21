package com.mediamate.user;

import com.mediamate.controller.profile_info.request.InitialRequest;
import com.mediamate.model.cost.realestate.RealEstate;
import com.mediamate.controller.register.token.Token;
import com.mediamate.user.role.UserRole;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity


public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String firstName;
    private String lastName;

    @OneToMany(
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            mappedBy = "user"
    )
    private List <RealEstate> realEstates = new ArrayList<>();
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



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<RealEstate> getRealEstates() {
        return realEstates;
    }

    public void setRealEstates(List<InitialRequest.RealEstateRequest> realEstateRequests) {
        this.realEstates =  realEstateRequests.stream()
                .map(realEstateRequest -> {
                        RealEstate realEstate = new RealEstate();
                        realEstate.setAddress(realEstateRequest.getAddress());
                        realEstate.addFlats(realEstateRequest.getFlats());
                        realEstate.setUser(this);
                    return realEstate;
                }).collect(Collectors.toList());
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
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.role));
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
