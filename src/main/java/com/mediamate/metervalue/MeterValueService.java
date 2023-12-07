package com.mediamate.metervalue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeterValueService {
    @Autowired
    MeterValueRepository meterValueRepository;

    public void addMeterValue(MeterValue meterValue){
        meterValueRepository.save(meterValue);
    }

    public MeterValue findMeterById (Long id){
        return meterValueRepository.findById(id).orElseThrow();
    }
}
