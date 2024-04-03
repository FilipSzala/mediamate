package com.mediamate.settlement;

import com.mediamate.YearMonthDate;
import com.mediamate.flat.Flat;
import com.mediamate.flat.FlatService;
import com.mediamate.image.Image;
import com.mediamate.image.ImageService;
import com.mediamate.meter.Meter;
import com.mediamate.meter.MeterOwnership;
import com.mediamate.meter.MeterService;
import com.mediamate.meter.MeterType;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import com.mediamate.settlement.request.MeterRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SettlementService {
    private final FlatService flatService;
    private final MeterService meterService;
    private final ImageService imageService;
    private final RealEstateService realEstateService;
    private MeterRequest meterRequestFromMemory;

    @Autowired
    public SettlementService(MeterService meterService, FlatService flatService, ImageService imageService,RealEstateService realEstateService) {
        this.meterService = meterService;
        this.flatService = flatService;
        this.imageService = imageService;
        this.realEstateService = realEstateService;
    }
    @Transactional
    public void createOrUpdateMeter(MeterRequest meterRequest, HttpSession httpSession) {
        MeterOwnership meterOwnership = meterRequest.getMeterOwnership();


        if(meterRequest.getYearMonthDate() == null){
            meterRequest.setYearMonthDateByLocaldate(LocalDate.now());
        }

        /*
        Image image = imageService.getImageById(meterRequest.getImageId()).orElseThrow();
*/

        Image image = new Image();

        if (isRealEstateOwnership(meterOwnership)) {
            createOrUpdateMeterWithRealEstate(meterRequest, httpSession, image);
        }
        else {
            createOrUpdateMeterWithFlat(meterRequest,image);
        }
    }

    private void createOrUpdateMeterWithFlat(MeterRequest meterRequest, Image image) {
        Long flatId = meterRequest.getFlatId();
        Flat flat = flatService.findFlatById(flatId);
        MeterType meterType = meterRequest.getMeterType();
        LocalDate createdAt = meterRequest.getYearMonthDate().toLocalDate();
        Meter meter;

        if (!doesMeterExistByDateAndTypeInFlat(flat,meterType, createdAt)) {
            meter = new Meter(
                    LocalDate.now()
            );
        }
        else {
            meter = meterService.getMeterByFlatAndTypeAndYearMonth(flat, meterType, createdAt).orElseThrow();
            meter.setCreatedAt(meterRequest.getYearMonthDate().toLocalDate());
        }
        meter.setMeterOwnership(MeterOwnership.FLAT);
        meter.setImage(image);
        setMeterFields(meterRequest, image, meter);
        flat.addMeter(meter);
        flatService.updateFlat(flat);
    }


    private void createOrUpdateMeterWithRealEstate(MeterRequest meterRequest, HttpSession httpSession, Image image) {
        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate = realEstateService.findById(realEstateId).get();
        LocalDate createdAt = meterRequest.getYearMonthDate().toLocalDate();
        MeterType meterType = meterRequest.getMeterType();
        Meter meter;
        if(!doesMeterExistByDateAndTypeInRealEstate(realEstate,meterType,LocalDate.now())) {
            meter = new Meter(
                    LocalDate.now()
            );
        }
        else {
            meter = meterService.getMeterByRealEstateAndTypeAndYearMonth(realEstate, meterType, createdAt).orElseThrow();
            meter.setCreatedAt(meterRequest.getYearMonthDate().toLocalDate());
        }
        meter.setMeterOwnership(MeterOwnership.REALESTATE);
        meter.setImage(image);
        setMeterFields(meterRequest, image, meter);
        realEstate.addMeter(meter);
            realEstateService.updateRealEstate(realEstate);
        }

    private void setMeterFields(MeterRequest meterRequest, Image image, Meter meter) {
        meter.setValue(meterRequest.getMeterValue());
        meter.setMeterType(meterRequest.getMeterType());
      /*  meter.setImage(image);
        image.setMeter(meter);*/
    }


    public String redirectForSetupMeter(MeterRequest meterRequestFromUser, Boolean userConfirmationReceived, HttpSession httpSession) {
        MeterRequest meterRequest = meterRequestFromUser.isEmpty()?   meterRequestFromMemory:meterRequestFromUser;
        YearMonthDate yearMonthDate = meterRequest.getYearMonthDate();
        MeterType meterType = meterRequest.getMeterType();
        Optional <Meter> meter = meterService.getMeterByYearMonthAndType(meterType,yearMonthDate);

        if(isMeterPresent(meter) && !isValueEmptyInMeter(meter) && !userConfirmationReceived) {
            meterRequestFromMemory = meterRequestFromUser;
            return "Existing meter value found in database. Are you sure you want to make change?";
        }
        createOrUpdateMeter(meterRequest,httpSession);
        return "Meter set up successfully.";
    }

    private void setImage (Image image,Meter meter){
        image.setMeter(meter);
        imageService.updateImagePartially(image.getId(),image);
    }



    private boolean doesMeterExistByDateAndTypeInFlat(Flat flat,MeterType meterType, LocalDate localDate){
        Optional <Meter> foundMeter =  meterService.getMeterByFlatAndTypeAndYearMonth(flat,meterType, localDate);
        return foundMeter.isPresent()? true:false;
    }
    private boolean doesMeterExistByDateAndTypeInRealEstate(RealEstate realEstate, MeterType meterType,LocalDate localDate){
        Optional <Meter> foundMeter = meterService.getMeterByRealEstateAndTypeAndYearMonth(realEstate,meterType,localDate);
        return foundMeter.isPresent()? true:false;
    }
    private boolean isMeterPresent(Optional<Meter> meter){
        return meter.isPresent()? true:false;
    }
    private Boolean isValueEmptyInMeter(Optional<Meter> meter){
        return meter.get().getValue() == 0.0? true:false;
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
    private boolean isRealEstateOwnership(MeterOwnership meterOwnership){
        if (meterOwnership.equals(MeterOwnership.REALESTATE)){
            return true;
        }
        return false;
    }

}
