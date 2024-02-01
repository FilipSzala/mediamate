package com.mediamate.summary.detaile_summary;

import com.mediamate.cost.mediaCost.MediaCost;
import com.mediamate.flat.Flat;
import com.mediamate.meter.Meter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class GasSummaryService {
    private BigDecimal gasConsumptionByFlatInGJ;
    private BigDecimal gasConsumptionByFlatInM3;
    private BigDecimal gasConsumptionByFlatInPercent = BigDecimal.ZERO;
    private BigDecimal gasConsumptionByRealEstateInM3;
    private BigDecimal gasConsumptionByRealEstateInGJ;


    public GasSummary createGasSummary(List<Flat> flats, MediaCost mediaCost, Meter lastMeterInFlat, Meter oneBeforeLastMeterInFlat, Meter lastMeterInRealEstate, Meter oneBeforeLastMeterInRealEstate, int flatCount){
        gasConsumptionByRealEstateInGJ = countGasConsumptionByRealEstateInGJ(flats);

        return GasSummary.builder()
                .gasPrice(mediaCost.getGas())
                .gasMeterValueInFlat(lastMeterInFlat.getGas())
                .gasMeterValueInRealEstate(lastMeterInRealEstate.getGas())
                .gasConsumptionByFlatInGJ(countGasConsumptionByFlatInGJ(lastMeterInFlat,oneBeforeLastMeterInFlat))
                .gasConsumptionByFlatInPercent(countGasConsumptionInPercent(gasConsumptionByFlatInGJ))
                .gasConsumptionByRealEstateInM3(countGasConsumptionByRealEstateInM3(lastMeterInRealEstate,oneBeforeLastMeterInRealEstate))
                .gasConsumptionByFlatInM3(countGasConsumptionByFlatInM3())
                .gasTotalPriceForFlat(countTotalGasPrice(gasConsumptionByFlatInM3,mediaCost))
                .gasTotalPriceForRealEstate(countTotalGasPrice(gasConsumptionByRealEstateInM3,mediaCost))
                .build();
    }
    private BigDecimal countGasConsumptionInPercent(BigDecimal gasConsumptionByFlat) {
        BigDecimal sumConsumptionGas = gasConsumptionByRealEstateInGJ;
        BigDecimal consumptionGasByFlat = gasConsumptionByFlat;
        BigDecimal percentMultiplier = new BigDecimal(100);
        gasConsumptionByFlatInPercent = gasConsumptionByFlat.multiply(percentMultiplier)
                .divide(sumConsumptionGas, 2, RoundingMode.UP);
        return gasConsumptionByFlatInPercent;
    }
    private BigDecimal countTotalGasPrice(BigDecimal gasConsumption, MediaCost mediaCost) {
        return gasConsumption.multiply(new BigDecimal(mediaCost.getGas())).setScale(2, RoundingMode.UP);
    }

    private BigDecimal countGasConsumptionByFlatInM3() {
        BigDecimal numberDivide = new BigDecimal(100);
        gasConsumptionByFlatInM3 =  gasConsumptionByRealEstateInM3.multiply(gasConsumptionByFlatInPercent).divide(numberDivide,2,RoundingMode.UP);
        return gasConsumptionByFlatInM3;
    }

    private BigDecimal countGasConsumptionByRealEstateInM3(Meter lastMeterInRealEstate, Meter oneBeforeLastMeterInRealEstate ) {
        gasConsumptionByRealEstateInM3 = new BigDecimal(lastMeterInRealEstate.getGas()-oneBeforeLastMeterInRealEstate.getGas()).setScale(2,RoundingMode.UP);
        return  gasConsumptionByRealEstateInM3;
    }
    private BigDecimal countGasConsumptionByFlatInGJ(Meter lastMeterInFlat, Meter oneBeforeLastMeterInFlat ) {
        Double lastMeterValue = lastMeterInFlat.getGas();
        Double oneBeforeLastMeterValue = oneBeforeLastMeterInFlat.getGas();
        gasConsumptionByFlatInGJ = new BigDecimal((lastMeterValue - oneBeforeLastMeterValue)).setScale(2, RoundingMode.HALF_UP);
        return gasConsumptionByFlatInGJ;
    }

    private BigDecimal countGasConsumptionByRealEstateInGJ(List<Flat> flats) {
        BigDecimal gasConsumption = flats.stream()
                .map(flat ->countMeterConsumptionByflat(flat.getMeters().get(flat.getMeters().size()-1).getGas(),flat.getMeters().get(flat.getMeters().size()-2).getGas()))
                .reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2,RoundingMode.HALF_UP);
        return gasConsumption;
    }

    private BigDecimal countMeterConsumptionByflat(Double lastValue,Double oneBeforeLastValue) {
        Double lastMeterValue = lastValue;
        Double oneBeforeLastMeterValue = oneBeforeLastValue;
        BigDecimal consumptionByFlat = new BigDecimal((lastMeterValue - oneBeforeLastMeterValue)).setScale(2, RoundingMode.HALF_UP);
        return consumptionByFlat;
    }

}
