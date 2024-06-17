package com.mediamate.user.role.owner;

import com.mediamate.initialSetup.request.InitialRequest;
import com.mediamate.realestate.RealEstateService;
import com.mediamate.security.SecurityService;
import com.mediamate.user.User;
import com.mediamate.user.UserRepository;
import com.mediamate.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerRoleService {
    @Autowired
    OwnerRepository ownerRepository;
    UserService userService;
    RealEstateService realEstateService;
    SecurityService securityService;
    private final UserRepository userRepository;

    @Autowired
    public OwnerRoleService(UserService userService, RealEstateService realEstateService, SecurityService securityService,
                            UserRepository userRepository) {

        this.userService = userService;
        this.realEstateService = realEstateService;
        this.securityService = securityService;
        this.userRepository = userRepository;
    }
    public boolean hasRealEstate(User user) {
        if(user==null){
            return false;
        }
            return user.getRealEstates() != null ? true : false;
    }
    @Transactional
    public void uploadUser(InitialRequest initialRequest) {
        User user = securityService.findUserBySession();
        user.setRealEstates(initialRequest.getRealEstates());
        user.setFirstName(initialRequest.getProfileInfo().getFirstName());
        user.setLastName(initialRequest.getProfileInfo().getSecondName());
        userService.updateUser(user);
    }
}
