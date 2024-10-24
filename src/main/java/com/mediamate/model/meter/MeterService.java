package com.mediamate.model.meter;

import com.mediamate.model.flat.Flat;
import com.mediamate.model.flat.FlatService;
import com.mediamate.model.real_estate.RealEstate;
import com.mediamate.model.real_estate.RealEstateService;
import com.mediamate.controller.settlement.request.MeterRequest;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void createMeters (List<Meter> meters){
        meterRepository.saveAll(meters);
    }

    public Optional<Meter> getMeterByRealEstateAndTypeAndYearMonth(RealEstate realEstate, MeterType meterType, LocalDate localDate){
        int localDateMonth = localDate.getMonthValue();
        int localDateYear = localDate.getYear();
       return meterRepository.findMeterByRealEstateAndTypeAndYearMonth(realEstate, meterType, localDateYear, localDateMonth);
    }
    public Optional<Meter> getMeterByFlatIdAndMeterTypeAndYearMonth(MeterRequest meterRequest) {
        return meterRepository.findMeterByFlatIdAndMeterTypeAndYearMonth(meterRequest.getFlatId(), meterRequest.getMeterType(), MeterOwnership.FLAT, meterRequest.getYearMonthDate().getYear(), meterRequest.getYearMonthDate().getMonth());
    }

    public Optional<Meter> getMeterByRealEstateIdAndMeterTypeAndYearMonth(Long id, MeterRequest meterRequest) {
        return meterRepository.findMeterByRealEstateIdAndMeterTypeAndYearMonth(id,meterRequest.getMeterType(),MeterOwnership.REALESTATE,meterRequest.getYearMonthDate().getYear(),meterRequest.getYearMonthDate().getMonth());
    }

    public Meter getMeterByFlatIdAndMeterTypeInCurrentMonth(Long flatId, MeterType meterType) {
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonth().getValue();
        return meterRepository.findMeterByFlatIdAndMeterTypeAndYearMonth(flatId, meterType,MeterOwnership.FLAT, year, month).orElse(new Meter(0.0));
    }

    public Meter getMeterByFlatIdAndMeterTypeInOneBeforeLastMonth(Long flatId, MeterType meterType) {
        LocalDate localDate = LocalDate.now().minusMonths(1);
        int year = localDate.getYear();
        int month = localDate.getMonth().getValue();
        return meterRepository.findMeterByFlatIdAndMeterTypeAndYearMonth(flatId, meterType,MeterOwnership.FLAT, year, month).orElse(new Meter(0.0));
    }
    public Meter getLastMeterByFlatIdAndMeterType(Long flatId, MeterType meterType) {
        return meterRepository.findLatestMeterByFlatIdAndMeterType(flatId, meterType,MeterOwnership.FLAT).orElse(new Meter(0.0));
    }
    //this one

    public Meter getMeterByRealEstateIdAndMeterTypeInCurrentMonth(Long realEstateId, MeterType meterType) {
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonth().getValue();
        return meterRepository.findMeterByRealEstateIdAndMeterTypeAndYearMonth(realEstateId, meterType, MeterOwnership.REALESTATE, year, month).orElseThrow();
    }
    public Meter getMeterByRealEstateIdAndMeterTypeInOneBeforeLastMonth(Long realEstateId, MeterType meterType) {
        LocalDate localDate = LocalDate.now().minusMonths(1);
        int year = localDate.getYear();
        int month = localDate.getMonth().getValue();
        return meterRepository.findMeterByRealEstateIdAndMeterTypeAndYearMonth(realEstateId, meterType, MeterOwnership.REALESTATE, year, month).orElseThrow();
    }

    public List<Meter> fingLastMetersByFlatIdsInLastMonth (List<Flat> flats){
        List<Long> flatIds = flats.stream().map(flat->flat.getId()).collect(Collectors.toList());
       return meterRepository.findLastMetersByFlatIdsForColdAndWarmWater(flatIds);
    }
}
