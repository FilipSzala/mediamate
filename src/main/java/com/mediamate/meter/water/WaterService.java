package com.mediamate.meter.water;

import com.mediamate.meter.MeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaterService {
    @Autowired
    WaterRepository waterRepository;
    MeterService meterService;
    @Autowired
    public WaterService(MeterService meterService) {
        this.meterService = meterService;
    }

    public void  createWater (Water water, Long meterId){
        waterRepository.save(water);
        meterService.addWaterToMeter(water,meterId);
    }
    public void updateWater (Long meterValueId, Water updatedWater){
        Water water = meterService.findMeterById(meterValueId).getWater();
        water.setColdWater(updatedWater.getColdWater());
        water.setHotWater(updatedWater.getHotWater());
        waterRepository.save(water);
    }
}
