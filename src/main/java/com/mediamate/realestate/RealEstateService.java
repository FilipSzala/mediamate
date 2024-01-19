package com.mediamate.realestate;

import com.mediamate.flat.FlatService;
import com.mediamate.meter.Meter;
import com.mediamate.security.SecurityService;
import com.mediamate.initialSetup.request.RealEstateRequest;
import com.mediamate.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

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
            realEstateRepository.save(realEstate);
        }
        return realEstates;
    }
    public void setupRealEstates(List<RealEstateRequest> realEstatesRequest) {
        Long ownerId = securityService.findOwnerIdBySession();
        List<RealEstate> databaseRealEstates = findAllByOwnerId(ownerId);

        IntStream.range(0, databaseRealEstates.size())
                .forEach(index -> {
                    RealEstate realEstate = databaseRealEstates.get(index);
                    realEstate.setAddress(realEstatesRequest.get(index).getAddress());
                    realEstate.setFlats(flatService.createEmptyFlats(realEstatesRequest.get(index).getFlatCount()));
                    realEstateRepository.save(realEstate);
                });
    }
    public void updateRealEstatePartially(Long realEstateId, RealEstate modifiedRealEstate) {
        RealEstate databaseRealEstate = findById(realEstateId).orElseThrow();
        databaseRealEstate.setOwnerId(modifiedRealEstate.getOwnerId());
        databaseRealEstate.setAddress(modifiedRealEstate.getAddress());
        databaseRealEstate.setFlats(modifiedRealEstate.getFlats());
        realEstateRepository.save(databaseRealEstate);
    }

    public void addMeterToRealEstate(RealEstate realEstate, Meter meter) {
        realEstate.addMeterToAdministrationMeters(meter);
        updateRealEstatePartially(realEstate.getId(),realEstate);
    }
}
