package com.mediamate.owner;

import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import com.mediamate.security.SecurityService;
import com.mediamate.setting.OwnerRequest;
import com.mediamate.setting.RealEstateRequest;
import com.mediamate.user.User;
import com.mediamate.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OwnerService {
    @Autowired
    OwnerRepository ownerRepository;
    UserService userService;
    RealEstateService realEstateService;
    SecurityService securityService;
    @Autowired
    public OwnerService(UserService userService,RealEstateService realEstateService) {

        this.userService = userService;
        this.realEstateService = realEstateService;
    }

    public void createOwner(OwnerRequest ownerRequest){
        Set<RealEstate> realEstates = realEstateService.createEmptyRealEstate(ownerRequest.getRealEstateCount());
        Owner owner = new Owner(
                ownerRequest.getFirstName(),
                ownerRequest.getLastName(),
                realEstates
        );
        ownerRepository.save(owner);
        userService.addOwner(owner);
    }

    public List<Owner> displayOwner() {
        return ownerRepository.findAll();
    }

    public Optional<Owner> findById (Long ownerId){
        return ownerRepository.findById(ownerId);
    }

}
