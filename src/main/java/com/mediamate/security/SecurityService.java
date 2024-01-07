package com.mediamate.security;

import com.mediamate.owner.Owner;
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
        return findUserBySession().getOwner().getOwnerId();
    }

    public List<RealEstateDto> getRealEstatesDtoBySession() {
        Owner owner = findOwnerBySession();
        List <RealEstate> realEstates = owner.getRealEstates();
        List <RealEstateDto> realEstateDtos = RealEstateMapper.mapToRealEstateDtos(realEstates);
        return realEstateDtos;
    }

    private Owner findOwnerBySession() {
        User user = findUserBySession();
        Owner owner = user.getOwner();
        return owner;
    }

}
