package com.mediamate.flat;

import com.mediamate.meter.Meter;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import com.mediamate.security.SecurityService;
import com.mediamate.initialSetup.request.FlatRequest;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
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
        flat.setRealEstateId(realEstateId);
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
        databaseFlat.setRealEstateId(updatedFlat.getRealEstateId());
        databaseFlat.setRenters(updatedFlat.getRenters());
        databaseFlat.setMeters(updatedFlat.getMeters());
        flatRepository.save(databaseFlat);
    }

    public void addMeterToMap (Long flatId, Meter meter){
        Flat flat = findFlatById(flatId);
        flat.getMeters().put(YearMonth.now(), meter);
        partiallyUpdateFlat(flatId,flat);
    }

    public List<Flat> findFlats (){
        return flatRepository.findAll();
    }

    public List<Flat> createEmptyFlats (int numberOfFlats) {
        List <Flat> flats = new ArrayList<>();
        for (int i=0;i<numberOfFlats;i++){
            Flat flat = new Flat();
            flats.add(flat);
            flatRepository.save(flat);
        }
        return flats;
    }

    public void setupFlats(Long realEstateId, List<FlatRequest> flatRequests) {
        RealEstate modifyRealEstate = realEstateService.findById(realEstateId).get();
        List <Flat> databaseFlats = findFlatsByRealEstateId(realEstateId);
        List <Flat> setupFlats = new ArrayList<>();
        IntStream.range(0,databaseFlats.size())
                .forEach(index -> {
                    Flat flat = databaseFlats.get(index);
                    flat.setRenters(flatRequests.get(index).getRenters());
                    flat.setPhoneNumber(flatRequests.get(index).getPhoneNumber());
                    setupFlats.add(flat);
                });
        modifyRealEstate.setFlats(setupFlats);
        realEstateService.updateRealEstatePartially(realEstateId,modifyRealEstate);
    }
}
