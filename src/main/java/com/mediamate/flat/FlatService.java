package com.mediamate.flat;

import com.mediamate.meter.Meter;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import com.mediamate.security.SecurityService;
import com.mediamate.initialSetup.request.FlatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void createFlat (Long realEstateId, Flat flat){
        RealEstate realEstate = realEstateService.findById(realEstateId).get();
        flat.setRealEstate(realEstate);
        flatRepository.save(flat);
    }
    public Flat findFlatById(Long flatId){
        return flatRepository.findById(flatId).orElseThrow();
    }
    public List <Flat> findFlatsByRealEstateId (Long realEstateId){
       return flatRepository.findByRealEstateId(realEstateId);
    }
    public void partiallyUpdateFlat(Long flatId, Flat updatedFlat){
        Flat databaseFlat = findFlatById(flatId);
        Flat databaseFlat2 = databaseFlat;

        databaseFlat.setRealEstate(updatedFlat.getRealEstate());
        databaseFlat.setRentersFullName(updatedFlat.getRentersFullName());
        databaseFlat.setMeters(updatedFlat.getMeters());
        flatRepository.save(databaseFlat);
    }

    public void addMeterToFlat (Flat flat, Meter meter){
        flat.addMeter(meter);
        partiallyUpdateFlat(flat.getId(),flat);
    }
    public List<Flat> createEmptyFlats (int count) {
        List <Flat> flats = new ArrayList<>();
        for (int i=0;i<count;i++){
            Flat flat = new Flat();
            flats.add(flat);
        }
        return flats;
    }

    public void setupFlats(Long realEstateId, List<FlatRequest> flatRequests) {
        Flat flat = findFlatById(12L);
        Flat flatTest = new Flat();
        flatTest.setPhoneNumber(flatRequests.get(0).getPhoneNumber());
        flatTest.setRentersFullName(flatRequests.get(0).getRentersFullName());
        partiallyUpdateFlat(12L,flatTest);
    }
       /* RealEstate realEstate = realEstateService.findById(realEstateId).get();
        List <Flat> flats = findFlatsByRealEstateId(realEstateId);
        flats.stream().forEach(flat -> {
            flat.setRenterCount(flatRequests.get(flats.indexOf(flat)).getRenterCount());
            flat.setRentersFullName(flatRequests.get(flats.indexOf(flat)).getRentersFullName());
            flat.setPhoneNumber(flatRequests.get(flats.indexOf(flat)).getPhoneNumber());
            partiallyUpdateFlat(flat.getId(), flat);
        });
        realEstate.setFlats(flats);
        realEstateService.updateRealEstatePartially(realEstateId,realEstate);
    }*/
}
