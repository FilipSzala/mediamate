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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@Service
public class SummaryService {
    private Meter lastMeterInFlat;
    private Meter oneBeforeLastMeterInFlat;
    private Meter lastMeterInRealEstate;
    private Meter oneBeforeLastMeterInRealEstate;
    private MediaCost mediaCost;
    private int renterCount;
    private int flatCount;

    private RealEstateService realEstateService;
    private CostService costService;
    @Autowired
    public SummaryService(RealEstateService realEstateService,CostService costService) {
        this.realEstateService = realEstateService;
        this.costService = costService;
    }

    public List<Summary> getSummaries(HttpSession httpSession,LocalDate date){

        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate =realEstateService.findById(realEstateId).get();
        Cost cost = costService.getCostByRealEstateIdAndDate(realEstateId,date).get();

        mediaCost = cost.getMediaCostPrice();
        lastMeterInRealEstate = setLastMeterInRealEstate(realEstate);
        oneBeforeLastMeterInRealEstate = setOneBeforeLastMeterInRealEstate(realEstate);
        flatCount = realEstate.getFlats().size();

        List<Summary> summaries = new ArrayList<>();
        List <Flat> flats = realEstate.getFlats();

        for (int i = 0; i < flats.size(); i++) {

            Flat flat = flats.get(i);
            renterCount = flat.getRenetersCount();
            setLastMeter(flat);
            setOneBeforeLastMeterInFlat(flat);


            Summary summary = Summary.builder()
                    .rentersName(flat.getRentersFullName())
                    .waterPrice(setWaterPrice(mediaCost))
                    .gasPrice(setGasPrice(mediaCost))
                    .electricityPrice(setElectricityPrice(mediaCost,realEstate))
                    .additionalPrice(setAdditionalPrice(cost))
                    .build();

            summaries.add(summary);
        }
        return summaries;
    }

    private Meter setLastMeterInRealEstate(RealEstate realEstate) {
        return  realEstate.getAdministrationMeter().get(realEstate.getAdministrationMeter().size()-1);
    }
    private Meter setOneBeforeLastMeterInRealEstate(RealEstate realEstate) {
        return  realEstate.getAdministrationMeter().get(realEstate.getAdministrationMeter().size()-2);
    }

    private Double setWaterPrice (MediaCost mediaCost){
        Double waterCost = mediaCost.getWater();
        Double valueOfLastMeterColdWater = lastMeterInFlat.getWater().getColdWater();
        Double valueOfLastMeterHotWater = lastMeterInFlat.getWater().getHotWater();
        Double valueOfOneBeforeLastMeterColdWater = oneBeforeLastMeterInFlat.getWater().getHotWater();
        Double valueOfOneBeforeLastMeterHotWater = oneBeforeLastMeterInFlat.getWater().getColdWater();
        Double result =((valueOfOneBeforeLastMeterColdWater-valueOfLastMeterColdWater)+(valueOfOneBeforeLastMeterHotWater-valueOfLastMeterHotWater))*waterCost;
        return result;
    }
    private Double setGasPrice(MediaCost mediaCost){
        Double gasCost = mediaCost.getGas();
        Double valueOfLastGasMeter = lastMeterInFlat.getGas();
        Double valueOfOneBeforeLastGasMeter = oneBeforeLastMeterInFlat.getGas();
        Double result = (valueOfOneBeforeLastGasMeter - valueOfLastGasMeter) * gasCost;
        return result;
    }
    private Double setElectricityPrice(MediaCost mediaCost,RealEstate realEstate){
        Double electricityCost = mediaCost.getElectricity();
        Double valueOfLastElectricityMeter = lastMeterInFlat.getElectricity();
        Double valueOfOneBeforeLastElectricityMeter = oneBeforeLastMeterInFlat.getElectricity();
        Double administrationValueForFlat = divideAdministrationValueOfElectricityForFlat(realEstate);
        Double result = ((valueOfOneBeforeLastElectricityMeter-valueOfLastElectricityMeter)+administrationValueForFlat)*electricityCost;
        return result;
    }
    private Double setAdditionalPrice (Cost cost){
        List<AdditionalCost> additionalCosts = cost.getAdditionalsCost();
        Double result = additionalCosts.stream()
                .mapToDouble(additionalCost -> countAdditionalCost(additionalCost))
                .sum();
        return result;
    }
    private double countAdditionalCost(AdditionalCost additionalCost){
        ChargeType chargeType =additionalCost.getChargeType();
        int timePeriod =additionalCost.getTimePeriod().getValue();
        Double cost = additionalCost.getCost();

        if(chargeType.equals(ChargeType.PERSON)){
            return (cost*renterCount)/timePeriod;
        }
        else
            return (cost / flatCount / timePeriod);
    }


    private double divideAdministrationValueOfElectricityForFlat(RealEstate realEstate) {
        int flatCount = realEstate.getFlats().size();
        return (oneBeforeLastMeterInRealEstate.getElectricity()-lastMeterInRealEstate.getElectricity())/flatCount;
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
