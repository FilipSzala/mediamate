package com.mediamate.summary.detaile_summary;

import com.mediamate.cost.mediaCost.MediaCost;
import com.mediamate.meter.Meter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ElectricitySummaryService {

    BigDecimal adminConsumptionPerFlat = BigDecimal.ZERO;


    public ElectricitySummary createElectricity(MediaCost mediaCost, Meter lastMeterInFlat, Meter oneBeforeLastMeterInFlat, Meter lastMeterInRealEstate, Meter oneBeforeLastMeterInRealEstate, int flatCount){
        return ElectricitySummary.builder()
                .price(mediaCost.getElectricity())
                .meterValueInFlat(lastMeterInFlat.getValue())
                .consumptionByFlat(countMeterConsumptionByflat(lastMeterInFlat.getValue(),oneBeforeLastMeterInFlat.getValue()))
                .meterValueForAdministration(lastMeterInRealEstate.getValue())
                .adminConsumptionPerFlat(countAdminElectricityConsumptionPerFlat(lastMeterInRealEstate,oneBeforeLastMeterInRealEstate,flatCount))
                .totalPriceForFlat(countTotalElectricityPrice(mediaCost,lastMeterInFlat,oneBeforeLastMeterInFlat))
                .build();
    }
    private BigDecimal countTotalElectricityPrice(MediaCost mediaCost,Meter lastMeterInFlat, Meter oneBeforeLastMeterInFlat){
        BigDecimal electricityPrice = new BigDecimal(mediaCost.getElectricity());
        BigDecimal totalConsumption = countMeterConsumptionByflat(lastMeterInFlat.getValue(),oneBeforeLastMeterInFlat.getValue()).add(adminConsumptionPerFlat);
        BigDecimal totalElectricityCost = totalConsumption.multiply(electricityPrice).setScale(2, RoundingMode.UP);
        return totalElectricityCost;
    }

    private BigDecimal countAdminElectricityConsumptionPerFlat(Meter lastMeterInRealEstate, Meter oneBeforeLastMeterInRealEstate, int flatCount) {
        BigDecimal adminElectricityConsumption = new BigDecimal((lastMeterInRealEstate.getValue()-oneBeforeLastMeterInRealEstate.getValue())/flatCount).setScale(2,RoundingMode.HALF_UP);
        adminConsumptionPerFlat = adminElectricityConsumption;
        return adminConsumptionPerFlat;
    }
    protected BigDecimal countMeterConsumptionByflat(Double lastValue,Double oneBeforeLastValue) {
        Double lastMeterValue = lastValue;
        Double oneBeforeLastMeterValue = oneBeforeLastValue;
        BigDecimal consumptionByFlat = new BigDecimal((lastMeterValue - oneBeforeLastMeterValue)).setScale(2, RoundingMode.HALF_UP);
        return consumptionByFlat;
    }

}
