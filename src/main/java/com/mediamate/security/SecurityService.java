package com.mediamate.security;

import com.mediamate.user.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class SecurityService {

    public User findUserBySession (){
        User user =(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
    public Long findOwnerIdBySession(){
        return findUserBySession().getOwner().getOwnerId();
    }

}
