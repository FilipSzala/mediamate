package com.mediamate.realestate;

import com.mediamate.flat.FlatService;
import com.mediamate.security.SecurityService;
import com.mediamate.user.User;
import com.mediamate.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RealEstateService {
    @Autowired
    private RealEstateRepository realEstateRepository;
    private SecurityService securityService;
    @Autowired
    @Lazy
    private  FlatService flatService;

    UserService userService;
    public RealEstateService(SecurityService securityService,UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }

    public Optional<RealEstate> findById(Long id){
        return realEstateRepository.findById(id);
    }
    public List<RealEstate> findAllByOwnerId(Long id){
       return realEstateRepository.findByOwnerId(id);
    }

    public List<RealEstate> createEmptyRealEstate (int numberOfRealEstate){
        List <RealEstate> realEstates = new ArrayList<>();
        for (int i=0;i<numberOfRealEstate;i++) {
            RealEstate realEstate = new RealEstate();
            realEstates.add(realEstate);
        }
        return realEstates;
    }
    /*@Transactional
    public void setupRealEstates(List<RealEstateRequest> realEstatesRequest) {
        Long ownerId = securityService.findOwnerIdBySession();
        List<RealEstate> databaseRealEstates = findAllByOwnerId(ownerId);

        IntStream.range(0, databaseRealEstates.size())
                .forEach(index -> {
                    RealEstate realEstate = databaseRealEstates.get(index);
                    realEstate.setAddress(realEstatesRequest.get(index).getAddress());
                    List <Flat> flats = flatService.createEmptyFlats(realEstatesRequest.get(index).getFlatCount());
                    realEstate.addFlats(flats);
                    realEstateRepository.save(realEstate);
                });
    }*/

    public void updateRealEstate(RealEstate modifiedRealEstate) {
        realEstateRepository.save(modifiedRealEstate);
    }


    public List <RealEstateDto> findAllByLogInUser() {
        User user = securityService.findUserBySession();
        Long ownerId = user.getUserRole().getId();
        List<RealEstate> realEstates = findAllByOwnerId(ownerId);
        List<RealEstateDto> realEstateDtos = RealEstateMapper.mapToRealEstateDtos(realEstates);
        return realEstateDtos;
    }
}
