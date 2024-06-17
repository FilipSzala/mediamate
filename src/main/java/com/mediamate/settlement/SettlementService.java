package com.mediamate.settlement;

import com.mediamate.date.YearMonthDate;
import com.mediamate.flat.Flat;
import com.mediamate.flat.FlatService;
import com.mediamate.image.Image;
import com.mediamate.image.ImageType;
import com.mediamate.photo.PhotoInformationRequest;
import com.mediamate.photo.PhotoService;
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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SettlementService {
    private final FlatService flatService;
    private final MeterService meterService;
    private final PhotoService photoService;
    private final RealEstateService realEstateService;
    private MeterRequest meterRequestFromMemory;

    @Autowired
    public SettlementService(MeterService meterService, FlatService flatService, PhotoService photoService, RealEstateService realEstateService) {
        this.meterService = meterService;
        this.flatService = flatService;
        this.photoService = photoService;
        this.realEstateService = realEstateService;
    }
    @Transactional
    public void createOrUpdateMeter(MeterRequest meterRequest, HttpSession httpSession) {
        MeterOwnership meterOwnership = meterRequest.getMeterOwnership();

        if(meterRequest.getYearMonthDate().year == 0 || meterRequest.getYearMonthDate().month == 0 ){
            meterRequest.setYearMonthDateByLocaldate(LocalDate.now());
        }
        Image image = photoService.getImageById(meterRequest.getImageId()).orElseThrow();

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
        Meter meter;

        if (!doesMeterExistByDateAndTypeInFlat(meterRequest)) {
            meter = new Meter(
                    LocalDate.now()
            );
        }
        else {
            meter =  meterService.getMeterByFlatIdAndMeterTypeAndYearMonth(meterRequest).orElseThrow();
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
        meter.setImage(image);
        image.setMeter(meter);
    }


    public String redirectForSetupMeter(MeterRequest meterRequestFromUser, Boolean userConfirmationReceived, HttpSession httpSession) {
        MeterRequest meterRequest = meterRequestFromUser.isEmpty()?   meterRequestFromMemory:meterRequestFromUser;
        YearMonthDate yearMonthDate = meterRequest.getYearMonthDate();
        MeterType meterType = meterRequest.getMeterType();
        Optional <Meter> meter = getMeterForRealestateOrFlat(httpSession,meterRequest);
        if(isMeterPresent(meter) && !isValueEmptyInMeter(meter) && !userConfirmationReceived) {
            meterRequestFromMemory = meterRequestFromUser;
            return "Existing meter value found in database. Are you sure you want to make change?";
        }
        createOrUpdateMeter(meterRequest,httpSession);
        return "Meter set up successfully.";
    }

    private Optional<Meter> getMeterForRealestateOrFlat(HttpSession httpSession, MeterRequest meterRequest) {
        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        if(isRealEstateOwnership(meterRequest.getMeterOwnership())){
           return meterService.getMeterByRealEstateIdAndMeterTypeAndYearMonth(realEstateId,meterRequest);
        }
        else {
            return  meterService.getMeterByFlatIdAndMeterTypeAndYearMonth(meterRequest);
        }
    }

    private boolean doesMeterExistByDateAndTypeInFlat(MeterRequest meterRequest){
        Optional <Meter> foundMeter =  meterService.getMeterByFlatIdAndMeterTypeAndYearMonth(meterRequest);
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

    private boolean isRealEstateOwnership(MeterOwnership meterOwnership){
        if (meterOwnership.equals(MeterOwnership.REALESTATE)){
            return true;
        }
        return false;
    }

    public void createMeterWithPhotoAndInformation(List<MultipartFile> files, List <PhotoInformationRequest> infoRequests, HttpSession httpSession) {
        List<Image> images = photoService.createImages(files, ImageType.METER,httpSession);
        IntStream.range(0, infoRequests.size())
                .mapToObj(index -> {
                    Meter meter = new Meter();
                    meter.setImage(images.get(index));
                    meter.setFlat(flatService.findFlatById(infoRequests.get(index).getFlatId()));
                    meter.setRealEstate((RealEstate) httpSession.getAttribute("chosenRealEstateId"));
                    meter.setMeterOwnership(MeterOwnership.FLAT);
                    meter.setValue(infoRequests.get(index).getMeterValue());
                    meter.setMeterType(infoRequests.get(index).getMeterType());
                    meter.setCreatedAt(infoRequests.get(index).getYearMonthDate());
                    return meter;
                })
                .collect(Collectors.toList());
    }

}
