package com.mediamate.model.cost.meter;

import com.mediamate.model.cost.flat.FlatService;
import com.mediamate.model.cost.realestate.RealEstate;
import com.mediamate.model.cost.realestate.RealEstateService;
import com.mediamate.controller.settlement.request.MeterRequest;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

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

    public Optional<Meter> getMeterByRealEstateAndTypeAndYearMonth(RealEstate realEstate, MeterType meterType, LocalDate localDate){
        int localDateMonth = localDate.getMonthValue();
        int localDateYear = localDate.getYear();
       return meterRepository.findMeterByRealEstateAndTypeAndYearMonth(realEstate, meterType, localDateYear, localDateMonth);
    }
    public Optional<Meter> getMeterByFlatIdAndMeterTypeAndYearMonth(MeterRequest meterRequest) {
        return meterRepository.findMeterByFlatIdAndMeterTypeAndYearMonth(meterRequest.getFlatId(), meterRequest.getMeterType(), meterRequest.getYearMonthDate().getYear(), meterRequest.getYearMonthDate().getMonth());
    }

    public Optional<Meter> getMeterByRealEstateIdAndMeterTypeAndYearMonth(Long id, MeterRequest meterRequest) {
        return meterRepository.findMeterByRealEstateIdAndMeterTypeAndYearMonth(id,meterRequest.getMeterType(),meterRequest.getYearMonthDate().getYear(),meterRequest.getYearMonthDate().getMonth());
    }

    public Meter getMeterByFlatIdAndMeterTypeInCurrentMonth(Long flatId, MeterType meterType) {
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonth().getValue();
        return meterRepository.findMeterByFlatIdAndMeterTypeAndYearMonth(flatId, meterType, year, month).orElseThrow();
    }
    public Meter getMeterByFlatIdAndMeterTypeInOneBeforeLastMonth(Long flatId, MeterType meterType) {
        LocalDate localDate = LocalDate.now().minusMonths(1);
        int year = localDate.getYear();
        int month = localDate.getMonth().getValue();
        return meterRepository.findMeterByFlatIdAndMeterTypeAndYearMonth(flatId, meterType, year, month).orElseThrow();
    }

    public Meter getMeterByRealEstateIdAndMeterTypeInCurrentMonth(Long realEstateId, MeterType meterType) {
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonth().getValue();
        return meterRepository.findMeterByRealEstateIdAndMeterTypeAndYearMonth(realEstateId, meterType, year, month).orElseThrow();
    }
    public Meter getMeterByRealEstateIdAndMeterTypeInOneBeforeLastMonth(Long realEstateId, MeterType meterType) {
        LocalDate localDate = LocalDate.now().minusMonths(1);
        int year = localDate.getYear();
        int month = localDate.getMonth().getValue();
        return meterRepository.findMeterByRealEstateIdAndMeterTypeAndYearMonth(realEstateId, meterType, year, month).orElseThrow();
    }
}
