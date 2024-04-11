package com.mediamate.summary;

import com.mediamate.cost.additionalCost.AdditionalCost;
import com.mediamate.cost.mediaCost.CostService;
import com.mediamate.cost.mediaCost.MediaCost;
import com.mediamate.flat.Flat;
import com.mediamate.meter.MeterService;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import com.mediamate.summary.detaile_summary.AdditionalCostSum;
import com.mediamate.summary.detaile_summary.ElectricityConsumption;
import com.mediamate.summary.detaile_summary.GasConsumption;
import com.mediamate.summary.detaile_summary.WaterConsumption;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MediaSummaryService{
    private RealEstateService realEstateService;
    private ElectricityConsumption electricityConsumption;
    private CostService costService;
    private MeterService meterService;
    private GasConsumption gasConsumption;
    private WaterConsumption waterConsumption;
    private AdditionalCostSum additionalCostSum;

    @Autowired
    public MediaSummaryService(RealEstateService realEstateService,AdditionalCostSum additionalCostSum, ElectricityConsumption electricityConsumption, CostService costService, MeterService meterService, GasConsumption gasConsumption, WaterConsumption waterConsumption) {
        this.realEstateService = realEstateService;
        this.additionalCostSum = additionalCostSum;
        this.electricityConsumption = electricityConsumption;
        this.gasConsumption = gasConsumption;
        this.waterConsumption = waterConsumption;
        this.costService = costService;
        this.meterService = meterService;
    }

    public void createMediaSummaries(HttpSession httpSession){
        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate = realEstateService.findById(realEstateId).orElseThrow();
        MediaCost mediaCost = costService.findMediaCostByRealEstateIdInCurrentMonth(realEstateId, LocalDate.now());
        List <AdditionalCost> additionalCosts = costService.findAdditionalCostByRealEstateIdInCurrentMonth(realEstateId, LocalDate.now());
        List<Flat> flats = realEstate.getFlats();
        List<MediaSummary> mediaSummaries = new ArrayList<>();

        createMediaSummaries(additionalCosts, flats, mediaSummaries);
        double sumGasPerRealEstateInGJ = countSumGasPerRealEstateInGJ(mediaSummaries);
        setMediaCostInMediaSummaries(mediaCost, mediaSummaries, sumGasPerRealEstateInGJ);


    }

    private void setMediaCostInMediaSummaries(MediaCost mediaCost, List<MediaSummary> mediaSummaries, double sumGasPerRealEstateInGJ) {
        for (int i = 0; i < mediaSummaries.size() ; i++) {
            MediaSummary mediaSummary = mediaSummaries.get(i);
            double gasConsumptionPerFlatInM3 = gasConsumption.countConsumptionPerFlatInM3(sumGasPerRealEstateInGJ, mediaSummary);

            mediaSummary.setGasConsumptionPerFlatInM3(gasConsumptionPerFlatInM3);
            mediaSummary.setTotalElectricityCost(mediaSummary.getElectricityConsumptionInKW()* mediaCost.getElectricity());
            mediaSummary.setTotalGasCost(mediaSummary.getGasConsumptionPerFlatInM3()* mediaCost.getGas());
            mediaSummary.setTotalWaterCost(mediaSummary.getWaterConsumptionInM3() * mediaCost.getWater());
            mediaSummary.setSewarageCost(mediaSummary.getTotalWaterCost());
            mediaSummary.setTotalAllMediaCost(countTotalMediaCost(mediaSummary));
        }
    }

    private double countSumGasPerRealEstateInGJ(List<MediaSummary> mediaSummaries) {
        double sumGasPerRealEstateInGJ = mediaSummaries.stream()
                .mapToDouble(flat -> flat.getGasConsumptionPerFlatInGJ())
                .sum();
        return sumGasPerRealEstateInGJ;
    }

    private void createMediaSummaries(List<AdditionalCost> additionalCosts, List<Flat> flats, List<MediaSummary> mediaSummaries) {
        for (Flat flat: flats) {
            MediaSummary mediaSummary = new MediaSummary(
                    LocalDate.now(),
                    electricityConsumption.countElectricityConsumption(flat.getId()),
                    gasConsumption.countGasConsumptionPerFlatInGJ(flat.getId()),
                    gasConsumption.countGasConsumptionPerRealEstateInM3(flat.getId()),
                    waterConsumption.countWaterConsumption(flat.getId()),
                    additionalCostSum.countTotalAdditionalCost(additionalCosts,flat.getRenter().getRenterCount(), flats.size())
                     );
            mediaSummaries.add(mediaSummary);
        }
    }

    private double countTotalMediaCost (MediaSummary mediaSummary){
        double electricityCost = mediaSummary.getTotalElectricityCost();
        double gasCost = mediaSummary.getTotalGasCost();
        double waterCost = mediaSummary.getTotalWaterCost();
        double additionalCost = mediaSummary.getTotalAdditionalCost();
        double sewarageCost = mediaSummary.getSewarageCost();
        return electricityCost + gasCost + waterCost + additionalCost + sewarageCost;
        }


}
