package com.mediamate.flat;

import com.mediamate.metervalue.MeterValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FlatService {
    @Autowired
    FlatRepository flatRepository;

    public void addFlat (Long realEstateId, Flat flat){
        flat.setRealEstateId(realEstateId);
        flatRepository.save(flat);
    }
    public Flat findFlatById(Long flatId){
        return flatRepository.findById(flatId).orElseThrow();
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

}
