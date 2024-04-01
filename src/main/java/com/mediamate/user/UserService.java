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
    @Transactional
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
        User user=token.get().getUser();
        User modifyUser=token.get().getUser();
        modifyUser.setEnabled(true);
        updateUserPartially(user);
    }
    public void updateUserPartially(User user) {
        User databaseUser = findById(user.getId()).orElseThrow();
        databaseUser.setEmail(user.getEmail());
        databaseUser.setPassword(user.getPassword());
        databaseUser.setEnabled(user.getEnabled());
        databaseUser.setLocked(user.getLocked());
        databaseUser.setUserRole(user.getUserRole());
        userRepository.save(databaseUser);
    }

    public Boolean isUserRoleCreated(User user){
        return user.getUserRole()!=null? true:false;
    }


}