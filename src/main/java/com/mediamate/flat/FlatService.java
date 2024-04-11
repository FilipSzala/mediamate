package com.mediamate.flat;

import com.mediamate.meter.Meter;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import com.mediamate.security.SecurityService;
import com.mediamate.initialSetup.request.RenterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlatService {
    @Autowired
    private FlatRepository flatRepository;

    private SecurityService securityService;

    private RealEstateService realEstateService;
    @Autowired
    public FlatService(RealEstateService realEstateService, SecurityService securityService) {
        this.realEstateService = realEstateService;
        this.securityService = securityService;
    }

    public Flat findFlatById(Long flatId){
        return flatRepository.findById(flatId).orElseThrow();
    }
    public List <Flat> findFlatsByRealEstateId (Long realEstateId){
       return flatRepository.findByRealEstateId(realEstateId);
    }
    public void updateFlat(Flat updatedFlat){
        flatRepository.save(updatedFlat);
    }

    public List<Flat> createEmptyFlats (int count) {
        List <Flat> flats = new ArrayList<>();
        for (int i=0;i<count;i++){
            Flat flat = new Flat();
            flats.add(flat);
        }
        return flats;
    }
    @Transactional
    public void setupFlats(List<RenterRequest> renterRequests) {
        for (RenterRequest renterRequest : renterRequests){
            Renter renter = new Renter(
                    renterRequest.getRentersFullName(),
                    renterRequest.getRenterCount(),
                    renterRequest.getPhoneNumber()
            );
            Flat flat = findFlatById(renterRequest.getFlatId());
            flat.setRenter(renter);
            renter.setFlat(flat);
            updateFlat(flat);
        }
    }
}
