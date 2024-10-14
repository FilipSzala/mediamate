package com.mediamate.model.user;

import com.mediamate.controller.profile_info.request.InitialRequest;
import com.mediamate.model.token.Token;
import com.mediamate.model.token.TokenService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@NoArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private TokenService tokenService;
    @Autowired
    public UserService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow();
    }
    @Transactional
    public void createUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void enableUser (String tokenKey){
        Optional <Token> token = tokenService.findByKey(tokenKey);
        User user =token.get().getUser();
        User modifyUser=token.get().getUser();
        modifyUser.setEnabled(true);
        updateUser(user);
    }
    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }

    public boolean hasRealEstate(User user) {
        if(user==null){
            return false;
        }
        return user.getRealEstates().isEmpty()? false:true;
    }
    public boolean isOwnerSetup(User user){
        return hasRealEstate(user);
    }

    public Boolean isUserRole (){
        User user = findUserBySession();
        return user.getRole().equals("USER")?true:false;
    }

    public User findUserBySession () {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
    @Transactional
    public void setupUser(InitialRequest initialRequest) {
        User user = findUserBySession();
        user.setRealEstates(initialRequest.getRealEstates());
        user.setFirstName(initialRequest.getProfileInfo().getFirstName());
        user.setLastName(initialRequest.getProfileInfo().getSecondName());
        updateUser(user);
    }

    public Long getFlatIdForLoginUser(){
        User user = findUserBySession();
        Long flatId = user.getRenter().getFlat().getId();
        return flatId;
    }

}