package com.mediamate.service;

import com.mediamate.model.Flat;
import com.mediamate.repository.FlatRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlatService {
    @Autowired
    FlatRepository flatRepository;

    public void addFlat (Long realEstateId, Flat flat){
        flat.setRealEstateId(realEstateId);
        flatRepository.save(flat);
    }
}
