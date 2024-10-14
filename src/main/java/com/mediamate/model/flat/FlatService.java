package com.mediamate.model.flat;

import com.mediamate.model.real_estate.RealEstate;
import com.mediamate.model.real_estate.RealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlatService {
    @Autowired
    private FlatRepository flatRepository;
    private RealEstateService realEstateService;
    @Autowired
    public FlatService(RealEstateService realEstateService) {
        this.realEstateService = realEstateService;
    }

    public Flat findFlatById(Long flatId){
        Flat flat = flatRepository.findById(flatId).orElseThrow();

        return flat;
    }
    public List <Flat> findFlatsByRealEstateId (Long realEstateId){
       return flatRepository.findByRealEstateId(realEstateId);
    }
    public List<Long> findFlatsIdByRealEstateId(Long realEstateId){
        List<Flat> flats = findFlatsByRealEstateId(realEstateId);
        return flats.stream()
                .map(flat -> flat.getId())
                .collect(Collectors.toList());
    }
    public void updateFlat(Flat updatedFlat){
        flatRepository.save(updatedFlat);
    }

    public List<Flat> getFlatsInRealEstate (RealEstate realEstate){
        return realEstate.getFlats();
    }



}
