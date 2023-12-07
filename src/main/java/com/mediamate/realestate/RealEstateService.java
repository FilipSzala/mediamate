package com.mediamate.realestate;

import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealEstateService {
    @Autowired
    RealEstateRepository realEstateRepository;

    public void addRealEstate (Long ownerId, RealEstate realEstate){
        realEstate.setOwnerId(ownerId);
        realEstateRepository.save(realEstate);
    }
}