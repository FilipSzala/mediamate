package com.mediamate.summary.detaile_summary;

import com.mediamate.cost.mediaCost.MediaCost;
import com.mediamate.meter.Meter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class WaterSummaryService {
    BigDecimal wasteWaterConsumptionByFlat = BigDecimal.ZERO;
    BigDecimal waterConsumptionByFlat = BigDecimal.ZERO;
    public WaterSummary createWaterSummary (MediaCost mediaCost, Meter lastMeterInFlat, Meter oneBeforeLastMeterInFlat, Meter lastMeterInRealEstate, Meter oneBeforeLastMeterInRealEstate, int flatCount){
        return WaterSummary.builder()
                .waterPrice(mediaCost.getWater())
                .meterValueForHotWater(lastMeterInFlat.getWater().getHotWater())
                .meterValueForColdWater(lastMeterInFlat.getWater().getColdWater())
                .waterConsumptionByFlat(countWaterConsumptionByFlat(lastMeterInFlat,oneBeforeLastMeterInFlat))
                //Wastewater is always the same as the water used
                .wasteWaterConsumptionByFlat((wasteWaterConsumptionByFlat))
                .waterTotalPriceForFlat(countTotalWaterPrice(mediaCost))
                .build();
    }
    private BigDecimal countWaterConsumptionByFlat(Meter lastMeterInFlat, Meter oneBeforeLastMeterInFlat) {
        Double lastColdWater = lastMeterInFlat.getWater().getColdWater();
        Double lastHotWater = lastMeterInFlat.getWater().getHotWater();
        Double oneBeforeLastColdWater = oneBeforeLastMeterInFlat.getWater().getColdWater();
        Double oneBeforeLasHotWater = oneBeforeLastMeterInFlat.getWater().getHotWater();
        BigDecimal consumptionByFlat = new BigDecimal((lastHotWater-oneBeforeLasHotWater)+(lastColdWater-oneBeforeLastColdWater)).setScale(2, RoundingMode.HALF_UP);
       wasteWaterConsumptionByFlat = consumptionByFlat;
       waterConsumptionByFlat = consumptionByFlat;
        return consumptionByFlat;
    }

    private BigDecimal countTotalWaterPrice (MediaCost mediaCost){
        BigDecimal waterCost = new BigDecimal(mediaCost.getWater());
        BigDecimal numberMultiply = new BigDecimal(2); //I'm doubling amount of water because wastewater is always the same as the water used
        BigDecimal waterConsumptionWithWasteWater = waterConsumptionByFlat.multiply(numberMultiply);
        BigDecimal totalWaterCost = waterConsumptionWithWasteWater.multiply(waterCost).setScale(2, RoundingMode.HALF_UP);
        return totalWaterCost;
    }
}
