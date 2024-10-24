package com.mediamate.model.media_summary.detaile_summary;

import com.mediamate.model.flat.Flat;
import com.mediamate.model.flat.FlatService;
import com.mediamate.model.meter.MeterService;
import com.mediamate.model.meter.MeterType;
import com.mediamate.model.media_summary.MediaSummary;
import com.mediamate.model.real_estate.RealEstate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Getter
@Setter
@Service
public class GasConsumption {
    private double lastMeterInFlat;
    private double oneBeforeLastMeterInFlat ;
    private double lastMeterInRealEstate;
    private double oneBeforeLastMeterInRealEstate;

    private MeterService meterService;
    private FlatService flatService;

    @Autowired
    public GasConsumption(MeterService meterService, FlatService flatService) {
        this.meterService = meterService;
        this.flatService = flatService;
    }

    public double countGasConsumptionPerFlatInGJ(Long flatId) {
        this.lastMeterInFlat = meterService.getMeterByFlatIdAndMeterTypeInCurrentMonth(flatId, MeterType.GAS).getValue();
        this.oneBeforeLastMeterInFlat = meterService.getLastMeterByFlatIdAndMeterType(flatId, MeterType.GAS).getValue();
        double consumptionPerFlatInGJ = lastMeterInFlat - oneBeforeLastMeterInFlat;
        return consumptionPerFlatInGJ;
    }
    public double countGasConsumptionPerRealEstateInM3(Long flatId) {
        Flat flat = flatService.findFlatById(flatId);
        RealEstate realEstate = flat.getRealEstate();
        this.lastMeterInRealEstate = meterService.getMeterByRealEstateIdAndMeterTypeInCurrentMonth(realEstate.getId(),MeterType.GAS).getValue();
        this.oneBeforeLastMeterInRealEstate = meterService.getMeterByRealEstateIdAndMeterTypeInOneBeforeLastMonth(realEstate.getId(),MeterType.GAS).getValue();
        double consumptionPerRealEstateInM3 = lastMeterInRealEstate - oneBeforeLastMeterInRealEstate;
        return consumptionPerRealEstateInM3;
    }

    public double countConsumptionPerFlatInM3 (double gasPerRealEstateInGJ,int numberRentersInRealEstate, MediaSummary mediaSummary,double flatCount) {
        if (gasPerRealEstateInGJ == 0.0) {
            double renterCount = mediaSummary.getFlat().getRenter().getRenterCount();
            return mediaSummary.getGasConsumptionPerRealEstateInM3().doubleValue()/numberRentersInRealEstate*renterCount;
        } else {
            double gasPerFlatInGJ = mediaSummary.getGasConsumptionPerFlatInGJ().doubleValue();
            double gasPerRealEstateInM3 = mediaSummary.getGasConsumptionPerRealEstateInM3().doubleValue();
            double consumptionPerFlatInPercent = gasPerFlatInGJ * 100 / gasPerRealEstateInGJ;
            double gasConsumptionPerFlatInM3 = (consumptionPerFlatInPercent / 100) * gasPerRealEstateInM3;
            return gasConsumptionPerFlatInM3;
        }
    }
}
