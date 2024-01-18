package com.mediamate.summary;

import com.mediamate.cost.Cost;
import com.mediamate.cost.CostService;
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
    private Meter lastMeter;
    private Meter oneBeforeLastMeter;
    private MediaCost mediaCost;

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

        List<Summary> summaries;
        List <Flat> flats = realEstate.getFlats();
        for (int i = 0; i < flats.size(); i++) {

            Flat flat = flats.get(i);
            setLastMeter(flat);
            setOneBeforeLastMeter(flat);


            Summary summary = Summary.builder()
                    .rentersName(flat.getRenters())
                    .waterPrice(setWaterPrice(mediaCost))
                    .gasPrice(setGasPrice(mediaCost))
                    .electricityPrice(null)
                    .additionalPrice(null)
                    .build();
        }
        return new ArrayList<>();
    }
    private Double setWaterPrice (MediaCost mediaCost){
        Double waterCost = mediaCost.getWater();
        Double valueOfLastMeterColdWater = lastMeter.getWater().getColdWater();
        Double valueOfLastMeterHotWater = lastMeter.getWater().getHotWater();
        Double valueOfOneBeforeLastMeterColdWater = oneBeforeLastMeter.getWater().getHotWater();
        Double valueOfOneBeforeLastMeterHotWater = oneBeforeLastMeter.getWater().getColdWater();
        Double result =((valueOfOneBeforeLastMeterColdWater-valueOfLastMeterColdWater)+(valueOfOneBeforeLastMeterHotWater-valueOfLastMeterHotWater))*waterCost;
        return result;
    }
    private Double setGasPrice(MediaCost mediaCost){
        Double gasCost = mediaCost.getGas();
        Double valueOfLastGasMeter = lastMeter.getGas();
        Double valueOfOneBeforeLastGasMeter = oneBeforeLastMeter.getGas();
        Double result = (valueOfOneBeforeLastGasMeter - valueOfLastGasMeter) * gasCost;
        return result;
    }
    private Double setElectricityPrice(MediaCost mediaCost,RealEstate realEstate){
        return lastMeter.getElectricity();
    }
    private void setLastMeter (Flat flat){
        List <Meter> meters =findMetersByFlat(flat);
        Meter meter = getMeterLastObject(meters);
        lastMeter = meter;
    }
    private void setOneBeforeLastMeter (Flat flat){
        List <Meter> meters =findMetersByFlat(flat);
        Meter meter = getMeterOneBeforeLastObject(meters);
        oneBeforeLastMeter = meter;
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
