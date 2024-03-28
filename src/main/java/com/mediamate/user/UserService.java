package com.mediamate.user;

import com.mediamate.user.role.owner.Owner;
import com.mediamate.register.token.Token;
import com.mediamate.register.token.TokenService;
import com.mediamate.security.SecurityService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    private TokenService tokenService;
    private SecurityService securityService;
    @Autowired
    public UserService(TokenService tokenService, SecurityService securityService) {
        this.tokenService = tokenService;
        this.securityService = securityService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findByEmail(email).orElseThrow();
    }
    public void createUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
    public Optional<User> findById (Long userId){
        return userRepository.findById(userId);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void enableUser (String tokenKey){
        Optional <Token> token = tokenService.findByKey(tokenKey);
        Long  userId=token.get().getUser().getId();
        User modifyUser=token.get().getUser();
        modifyUser.setEnabled(true);
        updateUserPartially(userId,modifyUser);
    }
    public void updateUserPartially(Long userId,User modifiedUser) {
        User databaseUser = findById(userId).orElseThrow();
        databaseUser.setEmail(modifiedUser.getEmail());
        databaseUser.setPassword(modifiedUser.getPassword());
        databaseUser.setEnabled(modifiedUser.getEnabled());
        databaseUser.setLocked(modifiedUser.getLocked());
        databaseUser.setUserRole(modifiedUser.getUserRole());
        databaseUser.setUserRole(modifiedUser.getUserRole());
        userRepository.save(databaseUser);
    }

    public Boolean isOwnerRole(User user){
        return Owner.class.equals(user.getUserRole()) ? true:false;
    }


}