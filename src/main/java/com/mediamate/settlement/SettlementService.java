package com.mediamate.settlement;

import com.mediamate.YearMonthResult;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SettlementService {
    private final FlatService flatService;
    private final MeterService meterService;
    private final WaterService waterService;
    private final ImageService imageService;
    private MeterRequest meterRequestFromMemory;

    @Autowired
    public SettlementService(MeterService meterService, WaterService waterService, FlatService flatService, ImageService imageService) {
        this.meterService = meterService;
        this.waterService = waterService;
        this.flatService = flatService;
        this.imageService = imageService;
    }
    public void setupMeter(MeterRequest meterRequest) {
        Long flatId = meterRequest.getFlatId();
        Flat flat = flatService.findFlatById(flatId);
        MeterType meterType = meterRequest.getMeterType();
        Image image = imageService.getImageById(meterRequest.getImageId()).orElseThrow();


        if(!doesMeterExistInCurrentMonth(flatId)){
            meterService.createMeter(new Meter(),flat);
        }
        List <Meter> meters = flatService.findFlatById(flatId).getMeters();
        Meter meter = getMeterInCurrentMonth(meters).get();
        setImage(image,meter);
        setMeterValueByType(meterRequest,meterType,meter);
        
        Long meterId = meter.getId();
        
        meterService.partiallyUpdateMeter(meterId,meter);
    }

    public String redirectForSetupMeter(MeterRequest meterRequestFromUser, Boolean userConfirmationReceived) {
        MeterRequest meterRequest = meterRequestFromUser.isEmpty()? meterRequestFromMemory:meterRequestFromUser;
        Long flatId = meterRequest.getFlatId();
        YearMonthResult yearMonthResult = meterRequest.getYearMonthResult();
        List<Meter> meters = meterService.getMetersByFlatIdAndYearMonth(flatId, yearMonthResult);
        MeterType meterType = meterRequest.getMeterType();
        if(doMetersExistByYearAndMonth(flatId, yearMonthResult) && !isMeterTypeValueEmpty(meters, meterType) && !userConfirmationReceived) {
            meterRequestFromMemory = meterRequestFromUser;
            return "Existing meter value found in database. Are you sure you want to make change?";
        }
        setupMeter(meterRequest);
        return "Meter set up successfully.";
    }
    private void setImage (Image image,Meter meter){
        image.setMeter(meter);
        imageService.updateImagePartially(image.getId(),image);
    }

    private void setMeterValueByType(MeterRequest meterRequest, MeterType meterType, Meter meter) {
        if (isElectricityType(meterType)){
            meter.setElectricity(meterRequest.getMeterValue());
        }
        if (isGasType(meterType)){
            meter.setGas(meterRequest.getMeterValue());
        }

        Long meterId = meter.getId();

        if (!doesWaterExist(meterId)){
            waterService.createWater(new Water(),meterId);
        }

        Water water = meter.getWater();

        if (isColdWaterType(meterType)){
            water.setColdWater(meterRequest.getMeterValue());
        }
        if(isHotWaterType(meterType)){
            water.setHotWater(meterRequest.getMeterValue());
        }
    }

    private boolean doesMeterExistInCurrentMonth(Long flatId){
        Flat flat = flatService.findFlatById(flatId);
        List<Meter> meters = flat.getMeters();
        Optional <Meter> foundMeter=getMeterInCurrentMonth(meters);
        return foundMeter.isPresent()? true:false;
    }
    private boolean doMetersExistByYearAndMonth(Long flatId, YearMonthResult yearMonthResult){
        List <Meter> meters = meterService.getMetersByFlatIdAndYearMonth(flatId,yearMonthResult);
        return !meters.isEmpty()? true:false;
    }
    private Boolean isMeterTypeValueEmpty(List<Meter> meters, MeterType meterType){
        if(meterType.equals(MeterType.ELECTRICITY)) {
            return meters.stream()
                    .anyMatch(meter -> meter.getElectricity() == 0.0);
        }
        if(meterType.equals(MeterType.GAS)) {
            return  meters.stream()
                    .anyMatch(meter -> meter.getGas() == 0.0);
        }
        if(meterType.equals(MeterType.COLD_WATER)||meterType.equals(MeterType.HOT_WATER)){
            return meters.stream()
                    .anyMatch(meter -> meter.getWater() == null);
        }
        return true;
    }


    private Optional<Meter> getMeterInCurrentMonth (List<Meter> meters){
        Optional <Meter> foundMeter= meters.stream()
                .filter(meter -> isSameYearAndMonth(meter.getCreatedAt(),LocalDate.now()))
                .findFirst();
        return foundMeter;
    }

    private boolean isSameYearAndMonth(LocalDate dateInDB, LocalDate currentDate) {
        return dateInDB.getYear() == currentDate.getYear() && dateInDB.getMonth() == currentDate.getMonth();
    }

    private boolean doesWaterExist(Long meterId){
        Meter meter = meterService.findMeterById(meterId);

        return  meter.getWater()!=null?true:false;
    }
    private boolean isElectricityType (MeterType type){
        return type.toString().equals("ELECTRICITY")? true:false;
    }
    private boolean isGasType (MeterType type){
        return type.toString().equals("GAS")? true:false;
    }
    private boolean isColdWaterType (MeterType type){
        return  type.toString().equals("COLD_WATER")? true:false;
    }
    private boolean isHotWaterType(MeterType type){
        return type.toString().equals("HOT_WATER")? true:false;
    }

}
