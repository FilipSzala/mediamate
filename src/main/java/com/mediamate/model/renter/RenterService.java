package com.mediamate.model.renter;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class RenterService {
    @Autowired
    private RenterRepository renterRepository;
    public Renter findRenterById(Long renterId){
        return renterRepository.findById(renterId).orElseThrow();
    }
}
