package com.mediamate.service;

import com.mediamate.model.MeterValue;
import com.mediamate.repository.MeterValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeterValueService {
    @Autowired
    MeterValueRepository meterValueRepository;

    public void addMeterValue(MeterValue meterValue){
        meterValueRepository.save(meterValue);
    }
    private void addMeterValueToMap(Long flatId,MeterValue meterValue){

    }

    public MeterValue findMeterById (Long id){
        return meterValueRepository.findById(id).orElseThrow();
    }
}
