package com.mediamate.summary.detaile_summary;

import com.mediamate.cost.mediaCost.MediaCost;
import com.mediamate.meter.Meter;
import com.mediamate.summary.MediaSummaryService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ElectricitySummaryService {

    BigDecimal adminElectricityConsumptionPerFlat = BigDecimal.ZERO;


    public ElectricitySummary createElectricity(MediaCost mediaCost, Meter lastMeterInFlat, Meter oneBeforeLastMeterInFlat, Meter lastMeterInRealEstate, Meter oneBeforeLastMeterInRealEstate, int flatCount){
        return ElectricitySummary.builder()
                .electricityPrice(mediaCost.getElectricity())
                .electricityMeterValueInFlat(lastMeterInFlat.getElectricity())
                .electricityConsumptionByFlat(countMeterConsumptionByflat(lastMeterInFlat.getElectricity(),oneBeforeLastMeterInFlat.getElectricity()))
                .electricityMeterValueForAdministration(lastMeterInRealEstate.getElectricity())
                .adminElectricityConsumptionPerFlat(countAdminElectricityConsumptionPerFlat(lastMeterInRealEstate,oneBeforeLastMeterInRealEstate,flatCount))
                .electricityTotalPriceForFlat(countTotalElectricityPrice(mediaCost,lastMeterInFlat,oneBeforeLastMeterInFlat))
                .build();
    }
    private BigDecimal countTotalElectricityPrice(MediaCost mediaCost,Meter lastMeterInFlat, Meter oneBeforeLastMeterInFlat){
        BigDecimal electricityPrice = new BigDecimal(mediaCost.getElectricity());
        BigDecimal totalConsumption = countMeterConsumptionByflat(lastMeterInFlat.getElectricity(),oneBeforeLastMeterInFlat.getElectricity()).add(adminElectricityConsumptionPerFlat);
        BigDecimal totalElectricityCost = totalConsumption.multiply(electricityPrice).setScale(2, RoundingMode.UP);
        return totalElectricityCost;
    }

    private BigDecimal countAdminElectricityConsumptionPerFlat(Meter lastMeterInRealEstate, Meter oneBeforeLastMeterInRealEstate, int flatCount) {
        BigDecimal adminElectricityConsumption = new BigDecimal((lastMeterInRealEstate.getElectricity()-oneBeforeLastMeterInRealEstate.getElectricity())/flatCount).setScale(2,RoundingMode.HALF_UP);
        adminElectricityConsumptionPerFlat = adminElectricityConsumption;
        return adminElectricityConsumptionPerFlat;
    }
    protected BigDecimal countMeterConsumptionByflat(Double lastValue,Double oneBeforeLastValue) {
        Double lastMeterValue = lastValue;
        Double oneBeforeLastMeterValue = oneBeforeLastValue;
        BigDecimal consumptionByFlat = new BigDecimal((lastMeterValue - oneBeforeLastMeterValue)).setScale(2, RoundingMode.HALF_UP);
        return consumptionByFlat;
    }

}
