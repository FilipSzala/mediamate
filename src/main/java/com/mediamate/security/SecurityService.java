package com.mediamate.security;

import com.mediamate.user.role.UserRole;
import com.mediamate.user.role.owner.Owner;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateDto;
import com.mediamate.realestate.RealEstateMapper;
import com.mediamate.user.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class SecurityService {

    public User findUserBySession (){
        User user =(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
    public Long findOwnerIdBySession(){
        UserRole userRole = findUserBySession().getUserRole();
        if(Owner.class.equals(userRole.getClass())) {
            Long ownerId = userRole.getId();
            return ownerId;
        }
        return null;
    }

    public List<RealEstateDto> getRealEstatesDtoBySession() {
        UserRole userRole = findUserRoleBySession();
        if(Owner.class.equals(userRole.getClass())) {
            List<RealEstate> realEstates = ((Owner) userRole).getRealEstates();
            List<RealEstateDto> realEstateDtos = RealEstateMapper.mapToRealEstateDtos(realEstates);
            return realEstateDtos;
        }
        else {
            return null;
        }
    }

    private UserRole findUserRoleBySession() {
        User user = findUserBySession();
        UserRole userrole = user.getUserRole();
        return userrole;
    }

}
