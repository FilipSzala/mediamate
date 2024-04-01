package com.mediamate.user.role.owner;

import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import com.mediamate.security.SecurityService;
import com.mediamate.initialSetup.request.OwnerRequest;
import com.mediamate.user.User;
import com.mediamate.user.UserRepository;
import com.mediamate.user.UserService;
import com.mediamate.user.role.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {
    @Autowired
    OwnerRepository ownerRepository;
    UserService userService;
    RealEstateService realEstateService;
    SecurityService securityService;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    @Autowired
    public OwnerService(UserService userService,RealEstateService realEstateService,SecurityService securityService,
                        UserRoleRepository userRoleRepository,
                        UserRepository userRepository) {

        this.userService = userService;
        this.realEstateService = realEstateService;
        this.securityService = securityService;
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createOwner(OwnerRequest ownerRequest){
        List<RealEstate> realEstates = realEstateService.createEmptyRealEstate(ownerRequest.getRealEstateCount());
        Owner owner = new Owner(
                ownerRequest.getFirstName(),
                ownerRequest.getLastName()
        );
        owner.addRealEstates(realEstates);
        User user = securityService.findUserBySession();
        user.setUserRole(owner);
        userService.updateUserPartially(user);
    }

    public List<Owner> displayOwner() {
        return ownerRepository.findAll();
    }

    public Optional<Owner> findById (Long ownerId){
        return ownerRepository.findById(ownerId);
    }

    public boolean hasRealEstate(Owner owner) {
        if(owner==null){
            return false;
        }
            return owner.getRealEstates() != null ? true : false;
    }
}
