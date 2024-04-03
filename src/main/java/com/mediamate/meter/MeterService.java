package com.mediamate.meter;

import com.mediamate.YearMonthDate;
import com.mediamate.flat.Flat;
import com.mediamate.flat.FlatService;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
    public Optional<Meter> getMeterByFlatAndTypeAndYearMonth(Flat flat, MeterType meterType, LocalDate localDate){
        int localDateMonth = localDate.getMonthValue();
        int localDateYear = localDate.getYear();
        return meterRepository.findMeterByFlatAndTypeAndYearMonth(flat, meterType, localDateYear, localDateMonth);
    }

    public Optional<Meter> getMeterByYearMonthAndType (MeterType meterType, YearMonthDate yearMonthDate){
        return meterRepository.findMeterByYearMonthDateAndMeterType(meterType, yearMonthDate.year, yearMonthDate.month);
    }

    public void updateMeter(Meter updatedMeter) {
        meterRepository.save(updatedMeter);
    }


}
