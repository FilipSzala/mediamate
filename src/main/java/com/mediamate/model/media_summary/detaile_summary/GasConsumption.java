package com.mediamate.model.cost.media_summary.detaile_summary;

import com.mediamate.model.cost.flat.Flat;
import com.mediamate.model.cost.flat.FlatService;
import com.mediamate.model.cost.meter.MeterService;
import com.mediamate.model.cost.meter.MeterType;
import com.mediamate.model.cost.media_summary.MediaSummary;
import com.mediamate.model.cost.realestate.RealEstate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
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
        this.oneBeforeLastMeterInFlat = meterService.getMeterByFlatIdAndMeterTypeInOneBeforeLastMonth(flatId, MeterType.GAS).getValue();
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

    public double countConsumptionPerFlatInM3 (double gasPerRealEstateInGJ, MediaSummary mediaSummary){
        double gasPerFlatInGJ = mediaSummary.getGasConsumptionPerFlatInGJ().doubleValue();
        double gasPerRealEstateInM3 = mediaSummary.getGasConsumptionPerRealEstateInM3().doubleValue();
        double consumptionPerFlatInPercent = gasPerFlatInGJ * 100 / gasPerRealEstateInGJ;
        double gasConsumptionPerFlatInM3 = (consumptionPerFlatInPercent/100) * gasPerRealEstateInM3;
        return gasConsumptionPerFlatInM3;
    }
}
