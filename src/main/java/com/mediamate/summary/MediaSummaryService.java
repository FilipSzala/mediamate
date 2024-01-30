package com.mediamate.summary;

import com.mediamate.cost.Cost;
import com.mediamate.cost.CostService;
import com.mediamate.cost.additionalCost.AdditionalCost;
import com.mediamate.cost.additionalCost.ChargeType;
import com.mediamate.cost.mediaCost.MediaCost;
import com.mediamate.flat.Flat;
import com.mediamate.meter.Meter;
import com.mediamate.meter.MeterService;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
public class MediaSummaryService {
    private Meter lastMeterInFlat;
    private Meter oneBeforeLastMeterInFlat;
    private Meter lastMeterInRealEstate;
    private Meter oneBeforeLastMeterInRealEstate;
    private MediaCost mediaCost;
    private BigDecimal gasConsumptionByFlatInGJ;
    private BigDecimal gasConsumptionByFlatInM3;
    private BigDecimal gasConsumptionByFlatInPercent = BigDecimal.ZERO;
    private BigDecimal gasConsumptionByRealEstateInGJ;
    private BigDecimal gasConsumptionByRealEstateInM3;

    private int flatCount;

    private RealEstateService realEstateService;
    private CostService costService;
    private MeterService meterService;
    @Autowired
    public MediaSummaryService(RealEstateService realEstateService, CostService costService, MeterService meterService) {
        this.realEstateService = realEstateService;
        this.costService = costService;
        this.meterService = meterService;
    }

    public List<MediaSummary> generateFlatSummaries(HttpSession httpSession, LocalDate date){

        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate =realEstateService.findById(realEstateId).get();
        Cost cost = costService.getCostByRealEstateIdAndDate(realEstateId,date).get();
        List <Flat> flats = realEstate.getFlats();

        mediaCost = cost.getMediaCostPrice();
        lastMeterInRealEstate = setLastMeterInRealEstate(realEstate);
        oneBeforeLastMeterInRealEstate = setOneBeforeLastMeterInRealEstate(realEstate);
        flatCount = realEstate.getFlats().size();
        gasConsumptionByRealEstateInGJ = countGasConsumptionByRealEstateInGJ(flats);

        List<MediaSummary> summaries = new ArrayList<>();


        summaries = createSummaries(realEstate, cost, flats);
        setSum(summaries);

        return summaries;
    }
    private List<MediaSummary> createSummaries(RealEstate realEstate, Cost cost, List<Flat> flats) {
        List<MediaSummary> summaries;
        summaries = flats.stream()
                .map(flat -> {
                    setLastMeter(flat);
                    setOneBeforeLastMeterInFlat(flat);


                    return MediaSummary.builder()
                            .createdAt(LocalDate.now())
                            .rentersName(flat.getRentersFullName())

                            .electricityPrice(mediaCost.getElectricity())
                            .electricityMeterValueInFlat(lastMeterInFlat.getElectricity())
                            .electricityConsumptionByFlat(countMeterConsumptionByflat(lastMeterInFlat.getElectricity(),oneBeforeLastMeterInFlat.getElectricity()))
                            .electricityMeterValueForAdministration(lastMeterInRealEstate.getElectricity())
                            .adminElectricityConsumptionPerFlat(countAdminElectricityConsumptionPerFlat())
                            .electricityTotalPriceForFlat(countTotalElectricityPrice())

                            .waterPrice(mediaCost.getWater())
                            .meterValueForHotWater(lastMeterInFlat.getWater().getHotWater())
                            .meterValueForColdWater(lastMeterInFlat.getWater().getColdWater())
                            .waterConsumptionByFlat(countWaterConsumptionByFlat())
                            //Wastewater is always the same as the water used
                            .wasteWaterConsumptionByFlat(countWaterConsumptionByFlat())
                            .waterTotalPriceForFlat(countTotalWaterPrice())


                            .gasPrice(mediaCost.getGas())
                            .gasMeterValueInFlat(lastMeterInFlat.getGas())
                            .gasMeterValueInRealEstate(lastMeterInRealEstate.getGas())
                            .gasConsumptionByFlatInGJ(countGasConsumptionByFlatInGJ())
                            .gasConsumptionByFlatInPercent(countGasConsumptionInPercent(gasConsumptionByFlatInGJ))
                            .gasConsumptionByRealEstateInM3(countGasConsumptionByRealEstateInM3())
                            .gasConsumptionByFlatInM3(countGasConsumptionByFlatInM3())
                            .gasTotalPriceForFlat(countTotalGasPrice(gasConsumptionByFlatInM3))
                            .gasTotalPriceForRealEstate(countTotalGasPrice(gasConsumptionByRealEstateInM3))


                            .additionalPrice(setAdditionalPrice(cost,flat.getRenetersCount()))
                            .build();
                })
                .collect(Collectors.toList());
        return summaries;
    }

    private BigDecimal countTotalGasPrice(BigDecimal gasConsumption) {
        return gasConsumption.multiply(new BigDecimal(mediaCost.getGas())).setScale(2,RoundingMode.UP);
    }

    private BigDecimal countGasConsumptionByFlatInM3() {
        BigDecimal numberDivide = new BigDecimal(100);
        gasConsumptionByFlatInM3 =  gasConsumptionByRealEstateInM3.multiply(gasConsumptionByFlatInPercent).divide(numberDivide,2,RoundingMode.UP);
        return gasConsumptionByFlatInM3;
    }

    private BigDecimal countGasConsumptionByRealEstateInM3() {
        gasConsumptionByRealEstateInM3 = new BigDecimal(lastMeterInRealEstate.getGas()-oneBeforeLastMeterInRealEstate.getGas()).setScale(2,RoundingMode.UP);
        return  gasConsumptionByRealEstateInM3;
    }

    private BigDecimal countGasConsumptionByRealEstateInGJ(List<Flat> flats) {
        BigDecimal gasConsumption = flats.stream()
                .map(flat ->countMeterConsumptionByflat(flat.getMeters().get(flat.getMeters().size()-1).getGas(),flat.getMeters().get(flat.getMeters().size()-2).getGas()))
                .reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2,RoundingMode.HALF_UP);
        return gasConsumption;
    }


    private void setSum(List<MediaSummary> summaries) {
        summaries.stream().forEach(mediaSummary -> mediaSummary.setTotalMediaSumByFlat(mediaSummary.getWaterTotalPriceForFlat().add(mediaSummary.getElectricityTotalPriceForFlat().add(mediaSummary.getGasTotalPriceForFlat().add(mediaSummary.getAdditionalPrice()))).setScale(2,RoundingMode.UP)));
    }


    private BigDecimal countGasConsumptionInPercent(BigDecimal gasConsumptionByFlat) {
        BigDecimal sumConsumptionGas = gasConsumptionByRealEstateInGJ;
        BigDecimal consumptionGasByFlat = gasConsumptionByFlat;
        BigDecimal percentMultiplier = new BigDecimal(100);
        gasConsumptionByFlatInPercent = gasConsumptionByFlat.multiply(percentMultiplier)
                .divide(sumConsumptionGas, 2, RoundingMode.UP);
        return gasConsumptionByFlatInPercent;
    }

    private Meter setLastMeterInRealEstate(RealEstate realEstate) {
        return  realEstate.getAdministrationMeter().get(realEstate.getAdministrationMeter().size()-1);
    }
    private Meter setOneBeforeLastMeterInRealEstate(RealEstate realEstate) {
        return  realEstate.getAdministrationMeter().get(realEstate.getAdministrationMeter().size()-2);
    }


    private BigDecimal setGasConsumption(){
        Double valueOfLastGasMeter = lastMeterInFlat.getGas();
        Double valueOfOneBeforeLastGasMeter = oneBeforeLastMeterInFlat.getGas();
        BigDecimal totalGasConsumpcion = new BigDecimal(valueOfLastGasMeter-valueOfOneBeforeLastGasMeter).setScale(2,RoundingMode.HALF_UP);
        return totalGasConsumpcion;
    }
    private BigDecimal countMeterConsumptionByflat(Double lastValue,Double oneBeforeLastValue) {
        Double lastMeterValue = lastValue;
        Double oneBeforeLastMeterValue = oneBeforeLastValue;
        BigDecimal consumptionByFlat = new BigDecimal((lastMeterValue - oneBeforeLastMeterValue)).setScale(2, RoundingMode.HALF_UP);
        return consumptionByFlat;
    }
    private BigDecimal countGasConsumptionByFlatInGJ() {
        Double lastMeterValue = lastMeterInFlat.getGas();
        Double oneBeforeLastMeterValue = oneBeforeLastMeterInFlat.getGas();
        gasConsumptionByFlatInGJ = new BigDecimal((lastMeterValue - oneBeforeLastMeterValue)).setScale(2, RoundingMode.HALF_UP);
        return gasConsumptionByFlatInGJ;
    }
    private BigDecimal countAdminElectricityConsumptionPerFlat() {
        BigDecimal adminElectricityConsumption = new BigDecimal((lastMeterInRealEstate.getElectricity()-oneBeforeLastMeterInRealEstate.getElectricity())/flatCount).setScale(2,RoundingMode.HALF_UP);
        return adminElectricityConsumption;
    }

    private BigDecimal countTotalElectricityPrice(){
        BigDecimal electricityPrice = new BigDecimal(mediaCost.getElectricity());
        BigDecimal totalConsumption = countMeterConsumptionByflat(lastMeterInFlat.getElectricity(),oneBeforeLastMeterInFlat.getElectricity()).add(countAdminElectricityConsumptionPerFlat());
        BigDecimal totalElectricityCost = totalConsumption.multiply(electricityPrice).setScale(2,RoundingMode.UP);
        return totalElectricityCost;
    }

    private BigDecimal countWaterConsumptionByFlat() {
        Double lastColdWater = lastMeterInFlat.getWater().getColdWater();
        Double lastHotWater = lastMeterInFlat.getWater().getHotWater();
        Double oneBeforeLastColdWater = oneBeforeLastMeterInFlat.getWater().getColdWater();
        Double oneBeforeLasHotWater = oneBeforeLastMeterInFlat.getWater().getHotWater();
        BigDecimal consumptionByFlat = new BigDecimal((lastHotWater-oneBeforeLasHotWater)+(lastColdWater-oneBeforeLastColdWater)).setScale(2, RoundingMode.HALF_UP);
        return consumptionByFlat;
    }

    private BigDecimal countTotalWaterPrice (){
        BigDecimal waterCost = new BigDecimal(mediaCost.getWater());
        BigDecimal numberMultiply = new BigDecimal(2); //I'm doubling amount of water because wastewater is always the same as the water used
        BigDecimal waterConsumptionWithWasteWater = countWaterConsumptionByFlat().multiply(numberMultiply);
        BigDecimal totalWaterCost = waterConsumptionWithWasteWater.multiply(waterCost).setScale(2, RoundingMode.HALF_UP);
        return totalWaterCost;
    }







    private BigDecimal setAdditionalPrice (Cost cost,int renterCount){
        List<AdditionalCost> additionalCosts = cost.getAdditionalsCost();
        Double sum = additionalCosts.stream()
                .mapToDouble(additionalCost -> countAdditionalCost(additionalCost,renterCount))
                .sum();
        BigDecimal totalAdditionalCost = new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP);
        return totalAdditionalCost;
    }
    private double countAdditionalCost(AdditionalCost additionalCost, int renterCount){
        ChargeType chargeType =additionalCost.getChargeType();
        int billingTimePeriod =additionalCost.getTimePeriod().getValue();
        Double additionalCostAmount = additionalCost.getCost();

        if(chargeType.equals(ChargeType.PERSON)){
            return (additionalCostAmount*renterCount)/billingTimePeriod;
        }
        else
            return (additionalCostAmount/flatCount/billingTimePeriod);
    }




    private void setLastMeter (Flat flat){
        List <Meter> meters =findMetersByFlat(flat);
        Meter meter = getMeterLastObject(meters);
        lastMeterInFlat = meter;
    }
    private void setOneBeforeLastMeterInFlat(Flat flat){
        List <Meter> meters =findMetersByFlat(flat);
        Meter meter = getMeterOneBeforeLastObject(meters);
        oneBeforeLastMeterInFlat = meter;
    }
    private List<Meter> findMetersByFlat(Flat flat){
        return flat.getMeters();
    }
    private Meter getMeterLastObject(List<Meter> meters){
        return meters.get(meters.size()-1);
    }
    private Meter getMeterOneBeforeLastObject(List<Meter>meters){
        return meters.get(meters.size()-2);
    }
}
