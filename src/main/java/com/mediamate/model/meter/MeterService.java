package com.mediamate.model.meter;

import com.mediamate.model.flat.FlatService;
import com.mediamate.model.image.request.ImageInformationRequest;
import com.mediamate.model.real_estate.RealEstate;
import com.mediamate.model.real_estate.RealEstateService;
import com.mediamate.controller.settlement.request.MeterRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
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

    public void createMeters(List<Meter> meters) {
        meterRepository.saveAll(meters);
    }

    public Optional<Meter> getMeterByRealEstateAndTypeAndYearMonth(RealEstate realEstate, MeterType meterType, LocalDate localDate) {
        int localDateMonth = localDate.getMonthValue();
        int localDateYear = localDate.getYear();
        return meterRepository.findMeterByRealEstateAndTypeAndYearMonth(realEstate, meterType, localDateYear, localDateMonth);
    }

    public Optional<Meter> getMeterByFlatIdAndMeterTypeAndYearMonth(MeterRequest meterRequest) {
        return meterRepository.findMeterByFlatIdAndMeterTypeAndYearMonth(meterRequest.getFlatId(), meterRequest.getMeterType(), MeterOwnership.FLAT, meterRequest.getYearMonthDate().getYear(), meterRequest.getYearMonthDate().getMonth());
    }

    public Optional<Meter> getMeterByRealEstateIdAndMeterTypeAndYearMonth(Long id, MeterRequest meterRequest) {
        return meterRepository.findMeterByRealEstateIdAndMeterTypeAndYearMonth(id, meterRequest.getMeterType(), MeterOwnership.REALESTATE, meterRequest.getYearMonthDate().getYear(), meterRequest.getYearMonthDate().getMonth());
    }

    public Meter getMeterByFlatIdAndMeterTypeInCurrentMonth(Long flatId, MeterType meterType) {
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonth().getValue();
        return meterRepository.findMeterByFlatIdAndMeterTypeAndYearMonth(flatId, meterType, MeterOwnership.FLAT, year, month).orElse(new Meter(0.0));
    }

    public Meter getMeterByFlatIdAndMeterTypeInOneBeforeLastMonth(Long flatId, MeterType meterType) {
        LocalDate localDate = LocalDate.now().minusMonths(1);
        int year = localDate.getYear();
        int month = localDate.getMonth().getValue();
        return meterRepository.findMeterByFlatIdAndMeterTypeAndYearMonth(flatId, meterType, MeterOwnership.FLAT, year, month).orElse(new Meter(0.0));
    }

    public Meter getLastMeterByFlatIdAndMeterType(Long flatId, MeterType meterType) {
        List<Meter> meters = meterRepository.findLatestMeterByFlatIdAndMeterTypeExcludingCurrentMonth(flatId, meterType, MeterOwnership.FLAT);
        return meters.stream().findFirst().orElse(new Meter((double) 0));
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

    public List<Meter> findLastMetersByFlatIdsInLastMonthForColdAndWarmWater(List<Long> flatIds) {
        return meterRepository.findLastMetersByFlatIdsForColdAndWarmWater(flatIds);
    }

    public List<Meter> findLastMetersByFlatIds(List<Long> flatIds) {
        return meterRepository.findLastMetersByFlatIds(flatIds);
    }

    public List<Meter> findLast12MetersByFlatOrRealEstateIdMeterTypeAndMeterOwnership(Long flatId, MeterType meterType, MeterOwnership meterOwnership,Boolean isFlat) {
        Pageable pageable = Pageable.ofSize(12);
        if (meterType.equals(MeterType.GAS)) {
            int currentMonth = LocalDate.now().getMonthValue();
            if (currentMonth >= 5 && currentMonth <= 9) {
                return meterRepository.findLast12MetersByFlatOrRealEstateIdMeterTypeAndMeterOwnershipInSummertime(flatId, meterType, meterOwnership,isFlat, pageable);
            } else {
                return meterRepository.findLast12MetersByFlatOrRealEstateIdMeterTypeAndMeterOwnershipInWinterTime(flatId, meterType, meterOwnership,isFlat, pageable);
            }
        } else {
            return meterRepository.findLast12MetersByFlatOrRealEstateIdMeterTypeAndOwnership(flatId, meterType, meterOwnership, isFlat, pageable);
        }
    }

    public List<String> meterValidation(List<ImageInformationRequest> infoRequests, HttpSession httpSession) {
        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        List<String> meterReadingValidationMessage = new ArrayList<>();
            List<Meter> meters = findLastMetersByRealestateId(realEstateId);

            for (Meter meter : meters) {
                infoRequests.stream()
                        .filter(infoRequest ->
                                infoRequest.getMeterType().equals(meter.getMeterType()) &&
                                        ((meter.getFlat() == null && infoRequest.getFlatId()==null) || (meter.getFlat()!= null && meter.getFlat().getId().equals(infoRequest.getFlatId())))
                        ).findAny()
                        .ifPresent(infoRequest -> {
                            double meterValue = infoRequest.getMeterValue() - meter.getValue();
                            String getMeterReadingValidationMessage = isMeterValueAcceptable(meter, infoRequest, meterValue);
                            boolean isMeterValueNotAccptable = getMeterReadingValidationMessage.equals("Correct value") ? false : true;
                            if (isMeterValueNotAccptable) {
                                meterReadingValidationMessage.add(new String(getMeterReadingValidationMessage));
                            }
                        });
            }

        return meterReadingValidationMessage;
    }

    private List<Meter> findLastMetersByRealestateId(Long realestateId) {
        return meterRepository.findLastMetersByRealestateIdWithMeterOwnershipRealEstate(realestateId);
    }

    private String isMeterValueAcceptable(Meter meter, ImageInformationRequest infoRequest, double value) {
        boolean isValueLowerOrEqualsZero = value <= 0 ? true : false;
        if (isValueLowerOrEqualsZero) {
            return infoRequest.getMeterType() + " meter for flat " + infoRequest.getFlatNumber() + " has a value of 0 or lower";
        }
        boolean isValueWithinAverageRange = isMeterRangeWithinLastTwelveReading(meter, value);
        if (isValueWithinAverageRange) {
            return "Correct value";
        } else {
            return "Incorrect value. " + infoRequest.getMeterType() + " meter for flat " + infoRequest.getFlatNumber() + " has a wrong avarege value";
        }
    }

    // In this method return 'true' for meters that have no more than 4 objects in the database, because it's not possible to calculate average value with such a small amount of data.
    private boolean isMeterRangeWithinLastTwelveReading(Meter meter, double value) {
        MeterType meterType = meter.getMeterType();
        MeterOwnership meterOwnership = meter.getMeterOwnership();
        Boolean isFlat=meterOwnership.equals(MeterOwnership.FLAT)?true:false;
        long id;
        if(isFlat){
            id = meter.getFlat().getId();
        }
        else {
            id = meter.getRealEstate().getId();
        }
        List<Meter> lastMeters = findLast12MetersByFlatOrRealEstateIdMeterTypeAndMeterOwnership(id, meterType, meterOwnership,isFlat);
        int meterNumberInArray;
        double totalConumsption = 0;
        boolean isLessThanFourReading = lastMeters.size() < 3 ? true : false;
        if (isLessThanFourReading) {
            return true;
        }
        for (meterNumberInArray = 0; meterNumberInArray < lastMeters.size() - 1; meterNumberInArray++) {
            totalConumsption += lastMeters.get(meterNumberInArray).getValue() - lastMeters.get(meterNumberInArray + 1).getValue();
        }

        double rangeValue = totalConumsption / lastMeters.size();

        boolean rangeEqualsZeroOrLower = rangeValue <= 0 ? true : false;
        if (rangeEqualsZeroOrLower) {
            return false;
        }

        boolean isValueWithinAverageRangePlusAndMinusMarginFifteenPercent = compareMeterValueWithAvarageRange(value, rangeValue);
        if (isValueWithinAverageRangePlusAndMinusMarginFifteenPercent) {
            return true;
        }

        return false;
    }


    private boolean compareMeterValueWithAvarageRange(double value, double rangeValue) {
        double fifteenPercentOfRangeValue = countFifteenPercentForRangeValue(rangeValue);
        if ((rangeValue - fifteenPercentOfRangeValue <= value) && (rangeValue + fifteenPercentOfRangeValue) >= value) {
            return true;
        }
        return false;
    }


    private double countFifteenPercentForRangeValue(double rangeValue) {
        return ((rangeValue * 0.15));
    }


}
