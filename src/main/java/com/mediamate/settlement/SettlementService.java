package com.mediamate.settlement;

import com.mediamate.flat.Flat;
import com.mediamate.flat.FlatService;
import com.mediamate.image.Image;
import com.mediamate.image.ImageService;
import com.mediamate.meter.Meter;
import com.mediamate.meter.MeterService;
import com.mediamate.meter.MeterType;
import com.mediamate.meter.water.Water;
import com.mediamate.meter.water.WaterService;
import com.mediamate.settlement.request.MeterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;

@Service
public class SettlementService {
    private final FlatService flatService;
    private final MeterService meterService;
    private final WaterService waterService;
    private final ImageService imageService;

    @Autowired
    public SettlementService(MeterService meterService, WaterService waterService, FlatService flatService, ImageService imageService) {
        this.meterService = meterService;
        this.waterService = waterService;
        this.flatService = flatService;
        this.imageService = imageService;
    }
    public void setupMeter(MeterRequest meterRequest) {
        Long flatId = meterRequest.getFlatId();
        MeterType meterType = meterRequest.getMeterType();
        Image image = imageService.getImageById(meterRequest.getImageId()).orElseThrow();

        if(!isMeterExistsInCurrentMonth(flatId)){
            meterService.createMeter(new Meter(),flatId);
        }

        Meter meter = flatService.findFlatById(flatId).getMeters().get(YearMonth.now());
        image.setMeterId(meter.getId());
        imageService.updateImagePartially(image.getId(),image);
        if (isElectricityType(meterType)){
            meter.setElectricity(meterRequest.getMeterValue());
        }
        if (isGasType(meterType)){
            meter.setGas(meterRequest.getMeterValue());
        }

        Long meterId = meter.getId();

        if (!isWaterExists(meterId)){
            waterService.createWater(new Water(),meterId);
        }

        Water water = meter.getWater();

        if (isColdWaterType(meterType)){
            water.setColdWater(meterRequest.getMeterValue());
        }
        if(isHotWaterType(meterType)){
            water.setHotWater(meterRequest.getMeterValue());
        }

        meterService.partiallyUpdateMeter(meterId,meter);
    }

    public boolean isMeterExistsInCurrentMonth(Long flatId){
        Flat flat = flatService.findFlatById(flatId);
        return flat.getMeters().containsKey(YearMonth.now())?true:false;
    }

    public boolean isWaterExists (Long meterId){
        Meter meter = meterService.findMeterById(meterId);

        return  meter.getWater()!=null?true:false;
    }
    public boolean isElectricityType (MeterType type){
        return type.toString().equals("ELECTRICITY")? true:false;
    }
    public boolean isGasType (MeterType type){
        return type.toString().equals("GAS")? true:false;
    }
    public boolean isColdWaterType (MeterType type){
        return  type.toString().equals("COLD_WATER")? true:false;
    }
    public boolean isHotWaterType(MeterType type){
        return type.toString().equals("HOT_WATER")? true:false;
    }
}
