package com.mediamate.flat;

import com.mediamate.meter.Meter;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import com.mediamate.security.SecurityService;
import com.mediamate.initialSetup.request.FlatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class FlatService {
    @Autowired
    FlatRepository flatRepository;

    SecurityService securityService;

    RealEstateService realEstateService;
    @Autowired
    public FlatService(RealEstateService realEstateService, SecurityService securityService) {
        this.realEstateService = realEstateService;
        this.securityService = securityService;
    }

    public void createFlat (Long realEstateId, Flat flat){
        RealEstate realEstate = realEstateService.findById(realEstateId).get();
        flat.setRealEstate(realEstate);
        flatRepository.save(flat);
    }
    public Flat findFlatById(Long flatId){
        return
                flatRepository.findById(flatId).orElseThrow();
    }
    public List <Flat> findFlatsByRealEstateId (Long realEstateId){
       return flatRepository.findByRealEstateId(realEstateId);
    }
    public void partiallyUpdateFlat(Long flatId, Flat updatedFlat){
        Flat databaseFlat = findFlatById(flatId);
        databaseFlat.setRealEstate(updatedFlat.getRealEstate());
        databaseFlat.setRentersFullName(updatedFlat.getRentersFullName());
        databaseFlat.setMeters(updatedFlat.getMeters());
        flatRepository.save(databaseFlat);
    }

    public void addMeterToFlat (Flat flat, Meter meter){
        flat.addMeterToMetersList(meter);
        partiallyUpdateFlat(flat.getId(),flat);
    }

    public List<Flat> findFlats (){
        return flatRepository.findAll();
    }

    public List<Flat> createEmptyFlats (int numberOfFlats, RealEstate realEstate) {
        List <Flat> flats = new ArrayList<>();
        for (int i=0;i<numberOfFlats;i++){
            Flat flat = new Flat();
            flat.setRealEstate(realEstate);
            flats.add(flat);
            flatRepository.save(flat);
        }
        return flats;
    }

    public void setupFlats(Long realEstateId, List<FlatRequest> flatRequests) {
        RealEstate realEstate = realEstateService.findById(realEstateId).get();
        List <Flat> flats = findFlatsByRealEstateId(realEstateId);
        flats.stream().forEach(flat -> {
            flat.setRenetersCount(flatRequests.get(flats.indexOf(flat)).getRenterCount());
            flat.setRentersFullName(flatRequests.get(flats.indexOf(flat)).getRentersFullName());
            flat.setPhoneNumber(flatRequests.get(flats.indexOf(flat)).getPhoneNumber());
        });
        realEstateService.updateRealEstatePartially(realEstateId,realEstate);
    }
}
