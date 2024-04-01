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
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import com.mediamate.settlement.request.MeterRequest;
import jakarta.servlet.http.HttpSession;
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
    private final RealEstateService realEstateService;
    private MeterRequest meterRequestFromMemory;

    @Autowired
    public SettlementService(MeterService meterService, WaterService waterService, FlatService flatService, ImageService imageService,RealEstateService realEstateService) {
        this.meterService = meterService;
        this.waterService = waterService;
        this.flatService = flatService;
        this.imageService = imageService;
        this.realEstateService = realEstateService;
    }
    public void setupMeter(MeterRequest meterRequest,HttpSession httpSession) {
        MeterType meterType = meterRequest.getMeterType();
        LocalDate dateOfMeterRequest = meterRequest.getYearMonthResult() == null ? LocalDate.now() : meterRequest.getYearMonthResult().toLocalDate();
        Image image = imageService.getImageById(meterRequest.getImageId()).orElseThrow();

        if (isAdministrationType(meterType)){
            setupMeterForAdministration(meterRequest,httpSession,dateOfMeterRequest,image);
            return;
        }
        Long flatId = meterRequest.getFlatId();
        Flat flat = flatService.findFlatById(flatId);


        if (!doesMeterExistByDateInFlat(flatId, dateOfMeterRequest)) {
            meterService.createMeterWithFlat(new Meter(),flat,dateOfMeterRequest);
        }

        List<Meter> meters = flatService.findFlatById(flatId).getMeters();
        Meter meter = getMeterByDate(meters,dateOfMeterRequest).get();
        setImage(image,meter);
        setMeterValueByType(meterRequest,meterType,meter);
        
        Long meterId = meter.getId();
        
        meterService.partiallyUpdateMeter(meterId,meter);
    }

    private void setupMeterForAdministration(MeterRequest meterRequest, HttpSession httpSession,LocalDate date,Image image) {
        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate = realEstateService.findById(realEstateId).get();
        if(!doesMeterExistByDateInRealEstate(realEstateId,date)){
            meterService.createMeter(new Meter(),realEstate,date);
        }
        List<Meter> meters = realEstateService.findById(realEstateId).get().getMeters();
        Meter meter = getMeterByDate(meters,date).get();
        setImage(image,meter);
        setMeterValueByType(meterRequest,meterRequest.getMeterType(),meter);

        Long meterId = meter.getId();

        meterService.partiallyUpdateMeter(meterId,meter);

    }

    public String redirectForSetupMeter(MeterRequest meterRequestFromUser, Boolean userConfirmationReceived,HttpSession httpSession) {
        MeterRequest meterRequest = meterRequestFromUser.isEmpty()? meterRequestFromMemory:meterRequestFromUser;
        Long flatId = meterRequest.getFlatId();
        YearMonthResult yearMonthResult = meterRequest.getYearMonthResult();
        List<Meter> meters = meterService.getMetersByFlatIdAndYearMonth(flatId, yearMonthResult);
        MeterType meterType = meterRequest.getMeterType();
        if(doMetersExistByYearAndMonth(flatId, yearMonthResult) && !isMeterTypeValueEmpty(meters, meterType) && !userConfirmationReceived) {
            meterRequestFromMemory = meterRequestFromUser;
            return "Existing meter value found in database. Are you sure you want to make change?";
        }
        setupMeter(meterRequest,httpSession);
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

    private boolean doesMeterExistByDateInFlat(Long flatId,LocalDate localDate){
        Flat flat = flatService.findFlatById(flatId);
        List<Meter> metersInFlat = flat.getMeters();
        Optional <Meter> foundMeter=getMeterByDate(metersInFlat,localDate);
        return foundMeter.isPresent()? true:false;
    }
    private boolean doesMeterExistByDateInRealEstate(Long realEstateId,LocalDate localDate){
        RealEstate realEstate = realEstateService.findById(realEstateId).get();
        List<Meter> metersInRealEstate = realEstate.getMeters();
        Optional <Meter> foundMeter=getMeterByDate(metersInRealEstate,localDate);
        return foundMeter.isPresent()? true:false;
    }
    private boolean doMetersExistByYearAndMonth(Long flatId, YearMonthResult yearMonthResult){
        List <Meter> meters = meterService.getMetersByFlatIdAndYearMonth(flatId,yearMonthResult);
        return !meters.isEmpty()? true:false;
    }
    private Boolean isMeterTypeValueEmpty(List<Meter> meters, MeterType meterType){
        if(meterType.equals(MeterType.ELECTRICITY_FLAT)) {
            return meters.stream()
                    .anyMatch(meter -> meter.getElectricity() == 0.0);
        }
        if(meterType.equals(MeterType.GAS_FLAT)) {
            return  meters.stream()
                    .anyMatch(meter -> meter.getGas() == 0.0);
        }
        if(meterType.equals(MeterType.COLD_WATER)){
            return meters.stream()
                    .anyMatch(meter -> meter.getWater().getColdWater() == 0.0);
        }
        if(meterType.equals(MeterType.HOT_WATER)){
            return meters.stream()
                    .anyMatch(meter -> meter.getWater().getHotWater() == 0.0);
        }
        return true;
    }


    private Optional<Meter> getMeterByDate (List<Meter> meters,LocalDate localDate){
        Optional <Meter> foundMeter= meters.stream()
                .filter(meter -> isSameYearAndMonth(meter.getCreatedAt(),localDate))
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

        return type.equals(MeterType.ELECTRICITY_FLAT)||type.equals(MeterType.ELECTRICITY_ADMINISTRATION)? true:false;
    }
    private boolean isGasType (MeterType type){
        return type.equals(MeterType.GAS_FLAT)||type.equals(MeterType.GAS_ADMINISTRATION)? true:false;
    }
    private boolean isColdWaterType (MeterType type){
        return  type.equals(MeterType.COLD_WATER)||type.equals(MeterType.WATER_ADMINISTRATION)? true:false;
    }
    private boolean isHotWaterType(MeterType type){
        return type.equals(MeterType.HOT_WATER)? true:false;
    }
    private boolean isAdministrationType(MeterType type){
        if (type.equals(MeterType.ELECTRICITY_ADMINISTRATION)||type.equals(MeterType.GAS_ADMINISTRATION)||type.equals(MeterType.WATER_ADMINISTRATION)){
            return true;
        }
        return false;
    }

}
