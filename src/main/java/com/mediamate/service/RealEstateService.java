package com.mediamate.service;

import com.mediamate.model.RealEstate;
import com.mediamate.repository.RealEstateRepository;
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
