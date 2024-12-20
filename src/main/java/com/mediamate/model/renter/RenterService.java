package com.mediamate.model.renter;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@NoArgsConstructor
public class RenterService {
    @Autowired
    private RenterRepository renterRepository;

    public RenterService(RenterRepository renterRepository) {
        this.renterRepository = renterRepository;
    }

    public Renter findRenterById(Long renterId){
        return renterRepository.findById(renterId).orElseThrow(() -> new IllegalArgumentException("Renter with ID " + renterId + " not found"));
    }
    public void updateSmsDateSent (Renter renter){
        renter.setSmsSentDate(LocalDate.now());
        renterRepository.save(renter);
    }
}
