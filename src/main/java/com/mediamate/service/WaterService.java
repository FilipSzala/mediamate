package com.mediamate.service;

import com.mediamate.model.Water;
import com.mediamate.repository.WaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaterService {
    @Autowired
    WaterRepository waterRepository;
    MeterValueService meterValueService;
    @Autowired
    public WaterService(MeterValueService meterValueService) {
        this.meterValueService = meterValueService;
    }

    public void  createWater (Water water){
        waterRepository.save(water);
    }
    public void updateWater (Long meterValueId, Water updatedWater){
        Water water = meterValueService.findMeterById(meterValueId).getWater();
        water.setColdWater(updatedWater.getColdWater());
        water.setHotWater(updatedWater.getHotWater());
        waterRepository.save(water);
    }
}
