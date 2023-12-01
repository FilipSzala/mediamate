package com.mediamate.service;

import com.mediamate.model.User;
import com.mediamate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void createUser (User user){
        user.setPassword(encryptPassword(user.getPassword()));
        if(user.isAuthentication()==true){
            user.setRole("Owner");
        }
        userRepository.save(user);
    }

    public String encryptPassword (String password){
        String hashedPassword = BCrypt.hashpw(password,BCrypt.gensalt());
        return hashedPassword;
    }
    public Boolean decryptPassword (String password,String hashedPassword){
        return BCrypt.checkpw(password,hashedPassword);
    }

}
