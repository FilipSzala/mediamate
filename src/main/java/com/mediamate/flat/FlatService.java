package com.mediamate.flat;

import com.mediamate.meterValue.MeterValue;
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
        flat.setRealEstateId(realEstateId);
        flatRepository.save(flat);
    }
    public Flat findFlatById(Long flatId){
        return flatRepository.findById(flatId).orElseThrow();
    }
    public List <Flat> findFlatsByRealEstateId (Long realEstateId){
       return flatRepository.findByRealEstateId(realEstateId);
    }
    public void updateFlat (Long flatId, Flat updatedFlat){
        Flat flat = findFlatById(flatId);
        flat.setRealEstateId(updatedFlat.getRealEstateId());
        flat.setRenters(updatedFlat.getRenters());
        flat.setMeterValues(updatedFlat.getMeterValues());
        flatRepository.save(flat);
    }

    public void addMeterValueToMap (Long flatId, MeterValue meterValue){
        Flat flat = findFlatById(flatId);
        flat.getMeterValues().put(LocalDate.now(),meterValue);
        updateFlat(flatId,flat);
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
        RealEstate databaseRealEstate = realEstateService.findById(realEstateId).get();
        RealEstate modifyRealEstate = databaseRealEstate;
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
        realEstateService.updateRealEstatePartially(databaseRealEstate,modifyRealEstate);
    }
}
