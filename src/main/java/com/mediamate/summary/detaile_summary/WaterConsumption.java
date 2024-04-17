package com.mediamate.summary.detaile_summary;

import com.mediamate.cost.mediaCost.CostService;
import com.mediamate.flat.Flat;
import com.mediamate.flat.FlatService;
import com.mediamate.meter.MeterService;
import com.mediamate.meter.MeterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class WaterConsumption {
    private double lastMeterInFlat;
    private double oneBeforeLastMeterInFlat;
    private FlatService flatService;
    private MeterService meterService;
    private Long flatId;
    private Long realEstateId;
    @Autowired
    public WaterConsumption(FlatService flatService, MeterService meterService) {
        this.flatService = flatService;
        this.meterService = meterService;
    }


    public double countWaterConsumption(Long flatId) {
        this.flatId = flatId;
        setMetersByMeterType(MeterType.COLD_WATER);
        double coldWaterConsumptionPerFlat = lastMeterInFlat - oneBeforeLastMeterInFlat;
        setMetersByMeterType(MeterType.HOT_WATER);
        double hotWaterConsumptionPerFlat =  lastMeterInFlat - oneBeforeLastMeterInFlat;
        double totalWaterConsumption = coldWaterConsumptionPerFlat + hotWaterConsumptionPerFlat;
        return totalWaterConsumption;
    }

    private void setMetersByMeterType(MeterType meterType) {
        this.lastMeterInFlat = meterService.getMeterByFlatIdAndMeterTypeInCurrentMonth(flatId, meterType).getValue();
        this.oneBeforeLastMeterInFlat = meterService.getMeterByFlatIdAndMeterTypeInOneBeforeLastMonth(flatId, meterType).getValue();
    }
}