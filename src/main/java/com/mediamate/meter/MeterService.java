package com.mediamate.meter;

import com.mediamate.YearMonthResult;
import com.mediamate.flat.Flat;
import com.mediamate.flat.FlatService;
import com.mediamate.meter.water.Water;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@NoArgsConstructor
public class MeterService {
    @Autowired
    MeterRepository meterRepository;
    FlatService flatService;
    RealEstateService realEstateService;
    @Autowired
    public MeterService(FlatService flatService, RealEstateService realEstateService) {
        this.flatService = flatService;
        this.realEstateService = realEstateService;
    }

    public void createMeterWithFlat(Meter meter,Flat flat, LocalDate date){
        meter.setCreatedAt(date);
        meter.setFlat(flat);
        meterRepository.save(meter);
        flatService.addMeterToFlat(flat,meter);
    }
    public void createMeterWithRealEstate(Meter meter, RealEstate realEstate, LocalDate date) {
        meter.setCreatedAt(date);
        meter.setRealEstate(realEstate);
        meterRepository.save(meter);
        realEstateService.addMeterToRealEstate(realEstate,meter);
    }
    public Meter findMeterById (Long id){
        return meterRepository.findById(id).orElseThrow();
    }

    public void addWaterToMeter(Water water,Long meterId) {
        Meter meter = findMeterById(meterId);
        meter.setWater(water);
        partiallyUpdateMeter(meterId,meter);
    }
    public List<Meter> getMetersByFlatIdAndYearMonth(Long flatId, YearMonthResult yearMonthResult){
        int year;
        int month;
        if(yearMonthResult!=null) {
            year = yearMonthResult.getYear();
            month = yearMonthResult.getMonth();
        }
        else {
            year = LocalDate.now().getYear();
            month = LocalDate.now().getMonthValue();
        }
        return meterRepository.findMetersByFlatIdAndYearMonth(flatId,year,month);
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
