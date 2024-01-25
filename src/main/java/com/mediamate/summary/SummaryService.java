package com.mediamate.summary;

import com.mediamate.cost.Cost;
import com.mediamate.cost.CostService;
import com.mediamate.cost.additionalCost.AdditionalCost;
import com.mediamate.cost.additionalCost.ChargeType;
import com.mediamate.cost.mediaCost.MediaCost;
import com.mediamate.flat.Flat;
import com.mediamate.meter.Meter;
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
public class SummaryService {
    private Meter lastMeterInFlat;
    private Meter oneBeforeLastMeterInFlat;
    private Meter lastMeterInRealEstate;
    private Meter oneBeforeLastMeterInRealEstate;
    private MediaCost mediaCost;
    private int flatCount;

    private RealEstateService realEstateService;
    private CostService costService;
    @Autowired
    public SummaryService(RealEstateService realEstateService,CostService costService) {
        this.realEstateService = realEstateService;
        this.costService = costService;
    }

    public List<Summary> generateFlatSummaries(HttpSession httpSession,LocalDate date){

        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate =realEstateService.findById(realEstateId).get();
        Cost cost = costService.getCostByRealEstateIdAndDate(realEstateId,date).get();

        mediaCost = cost.getMediaCostPrice();
        lastMeterInRealEstate = setLastMeterInRealEstate(realEstate);
        oneBeforeLastMeterInRealEstate = setOneBeforeLastMeterInRealEstate(realEstate);
        flatCount = realEstate.getFlats().size();

        List<Summary> summaries = new ArrayList<>();
        List <Flat> flats = realEstate.getFlats();

        summaries = createSummaries(realEstate, cost, flats);
        setupGasVariables(summaries);
        setSum(summaries);

        return summaries;
    }

    private List<Summary> createSummaries(RealEstate realEstate, Cost cost, List<Flat> flats) {
        List<Summary> summaries;
        summaries = flats.stream()
                .map(flat -> {
                    setLastMeter(flat);
                    setOneBeforeLastMeterInFlat(flat);

                    return Summary.builder()
                            .rentersName(flat.getRentersFullName())
                            .waterPrice(setWaterPrice(mediaCost))
                            .gasConsumption(setGasConsumption())
                            .electricityPrice(setElectricityPrice(mediaCost, realEstate))
                            .additionalPrice(setAdditionalPrice(cost,flat.getRenetersCount()))
                            .build();
                })
                .collect(Collectors.toList());
        return summaries;
    }

    private void setupGasVariables(List<Summary> summaries) {
        BigDecimal sumOfGasPriceForRealEstate = calculateGasPriceForRealEstate(mediaCost);
        Double sumOfConsumptionGasInRealEstate = countConsumptionGasInRealEstate(summaries);

        setGasConsumptionInPercent(sumOfConsumptionGasInRealEstate, summaries);
        setGasPrice(summaries, sumOfGasPriceForRealEstate);
    }

    private void setGasPrice(List<Summary> summaries, BigDecimal sumOfGasPriceForRealEstate) {
        summaries.stream().
                forEach(summary -> summary.setGasPrice(setGasPrice(sumOfGasPriceForRealEstate,summary.getGasConsumptionInPercent())));
    }

    private Double countConsumptionGasInRealEstate(List<Summary> summaries) {
        Double sumOfConsumptionGasInRealEstate = summaries.stream()
                .map(summary -> summary.getGasConsumption())
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
        return sumOfConsumptionGasInRealEstate;
    }

    private BigDecimal setGasPrice (BigDecimal sumOfGasPriceForRealEstate,BigDecimal gasConsumptionInPercent){
        return (gasConsumptionInPercent.divide(new BigDecimal(100)).multiply(sumOfGasPriceForRealEstate).setScale(2, RoundingMode.HALF_UP));
    }

    private void setSum(List<Summary> summaries) {
        summaries.stream().forEach(summary -> summary.setSum(summary.getWaterPrice().add(summary.getElectricityPrice().add(summary.getGasPrice().add(summary.getAdditionalPrice())))));
    }

    private BigDecimal calculateGasPriceForRealEstate(MediaCost mediaCost){
        Double gasPriceForM3 = mediaCost.getGas();
        BigDecimal totalGasPrice = new BigDecimal((lastMeterInRealEstate.getGas() -oneBeforeLastMeterInRealEstate.getGas()) * gasPriceForM3);
        return totalGasPrice;
    }


    

    private void setGasConsumptionInPercent(Double consumptionGas, List<Summary> summaries) {
        BigDecimal sumOfconsumptionGas = new BigDecimal(consumptionGas);
        BigDecimal percentMultiplier = new BigDecimal(100);

        summaries.forEach(summary -> {
            BigDecimal gasConsumptionPercent = summary.getGasConsumption()
                    .multiply(percentMultiplier)
                    .divide(sumOfconsumptionGas, 2, RoundingMode.HALF_UP);
            summary.setGasConsumptionInPercent(gasConsumptionPercent);
        });
    }

    private Meter setLastMeterInRealEstate(RealEstate realEstate) {
        return  realEstate.getAdministrationMeter().get(realEstate.getAdministrationMeter().size()-1);
    }
    private Meter setOneBeforeLastMeterInRealEstate(RealEstate realEstate) {
        return  realEstate.getAdministrationMeter().get(realEstate.getAdministrationMeter().size()-2);
    }

    private BigDecimal setWaterPrice (MediaCost mediaCost){
        Double waterCost = mediaCost.getWater();
        Double valueOfOneBeforeLastMeterColdWater = oneBeforeLastMeterInFlat.getWater().getColdWater();
        Double valueOfLastMeterColdWater = lastMeterInFlat.getWater().getColdWater();
        Double valueOfOneBeforeLastMeterHotWater = oneBeforeLastMeterInFlat.getWater().getHotWater();
        Double valueOfLastMeterHotWater = lastMeterInFlat.getWater().getHotWater();


        BigDecimal totalWaterCost = new BigDecimal(((valueOfLastMeterColdWater-valueOfOneBeforeLastMeterColdWater)+(valueOfLastMeterHotWater-valueOfOneBeforeLastMeterHotWater))*waterCost*2).setScale(2, RoundingMode.HALF_UP);
        return totalWaterCost;
    }
    private BigDecimal setGasConsumption(){
        Double valueOfLastGasMeter = lastMeterInFlat.getGas();
        Double valueOfOneBeforeLastGasMeter = oneBeforeLastMeterInFlat.getGas();
        BigDecimal totalGasConsumpcion = new BigDecimal(valueOfLastGasMeter-valueOfOneBeforeLastGasMeter).setScale(2,RoundingMode.HALF_UP);
        return totalGasConsumpcion;
    }
    private BigDecimal setElectricityPrice(MediaCost mediaCost,RealEstate realEstate){
        Double electricityCost = mediaCost.getElectricity();
        Double valueOfLastElectricityMeter = lastMeterInFlat.getElectricity();
        Double valueOfOneBeforeLastElectricityMeter = oneBeforeLastMeterInFlat.getElectricity();
        Double administrationValueForFlat = divideAdministrationValueOfElectricityForFlat(realEstate);
        BigDecimal totalElectricityCost = new BigDecimal(((valueOfLastElectricityMeter-valueOfOneBeforeLastElectricityMeter)+administrationValueForFlat)*electricityCost).setScale(2, RoundingMode.HALF_UP);
        return totalElectricityCost;
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


    private double divideAdministrationValueOfElectricityForFlat(RealEstate realEstate) {
        int flatCount = realEstate.getFlats().size();
        return (lastMeterInRealEstate.getElectricity()-oneBeforeLastMeterInRealEstate.getElectricity())/flatCount;
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
