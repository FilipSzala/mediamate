package com.mediamate.meter;

import com.mediamate.flat.Flat;
import com.mediamate.flat.FlatService;
import com.mediamate.meter.water.Water;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class MeterService {
    @Autowired
    MeterRepository meterRepository;
    FlatService flatService;
    @Autowired
    public MeterService(FlatService flatService) {
        this.flatService = flatService;
    }

    public void createMeter(Meter meter, Flat flat){
        meter.setFlat(flat);
        meterRepository.save(meter);
        flatService.addMeterToFlat(flat,meter);
    }
    public Meter findMeterById (Long id){
        return meterRepository.findById(id).orElseThrow();
    }

    public void addWaterToMeter(Water water,Long meterId) {
        Meter meter = findMeterById(meterId);
        meter.setWater(water);
        partiallyUpdateMeter(meterId,meter);
    }

    public void partiallyUpdateMeter(Long meterId, Meter updatedMeter) {
        Meter databaseMeter = findMeterById(meterId);
        databaseMeter.setElectricity(updatedMeter.getElectricity());
        databaseMeter.setGas(updatedMeter.getGas());
        databaseMeter.setWater(updatedMeter.getWater());
        databaseMeter.setFlat(updatedMeter.getFlat());
        meterRepository.save(databaseMeter);
    }
}
