package com.mediamate.realestate;

import com.mediamate.flat.FlatService;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateRepository;
import com.mediamate.security.SecurityService;
import com.mediamate.setting.RealEstateRequest;
import com.mediamate.user.User;
import com.mediamate.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Service
public class RealEstateService {
    @Autowired
    RealEstateRepository realEstateRepository;
    SecurityService securityService;
    FlatService flatService;

    UserService userService;
    @Autowired
    public RealEstateService(SecurityService securityService,FlatService flatService, UserService userService) {
        this.securityService = securityService;
        this.flatService = flatService;
        this.userService = userService;
    }

    public Optional<RealEstate> findById(Long id){
        return realEstateRepository.findById(id);
    }
    public Set<RealEstate> findAllByOwnerId(Long id){
       return realEstateRepository.findByOwnerId(id);
    }

    public Set<RealEstate> createEmptyRealEstate (int numberOfRealEstate){
        Set<RealEstate> realEstates = new HashSet<>();
        for (int i=0;i<numberOfRealEstate;i++) {
            RealEstate realEstate = new RealEstate();
            realEstates.add(realEstate);
            realEstateRepository.save(realEstate);
        }
        return realEstates;
    }
    public void setupRealEstates(List<RealEstateRequest> realEstatesRequest) {
        Long ownerId = securityService.findUserBySession().getOwner().getOwnerId();
        Set<RealEstate> databaseRealEstates = findAllByOwnerId(ownerId);

        IntStream.range(0, databaseRealEstates.size())
                .forEach(index -> {
                    RealEstate realEstate = findById(Long.valueOf(index+1)).get();
                    realEstate.setAddress(realEstatesRequest.get(index).getAddress());
                    realEstate.setFlats(flatService.createEmptyFlats(realEstatesRequest.get(index).getFlatCount()));
                    realEstateRepository.save(realEstate);
                });
    }
    public void updateRealEstatePartially(RealEstate databaseRealEstate, RealEstate modifiedRealEstate) {
        databaseRealEstate.setOwnerId(modifiedRealEstate.getOwnerId());
        databaseRealEstate.setAddress(modifiedRealEstate.getAddress());
        databaseRealEstate.setFlats(modifiedRealEstate.getFlats());
        realEstateRepository.save(databaseRealEstate);
    }
}
