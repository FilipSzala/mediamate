package com.mediamate.model.media_summary;

import com.mediamate.dto.TableAlleFeesMapper;
import com.mediamate.dto.TableAllFeesDto;
import com.mediamate.model.cost.CostService;
import com.mediamate.model.cost.additionalCost.AdditionalCost;
import com.mediamate.model.cost.media_cost.MediaCost;
import com.mediamate.model.flat.Flat;
import com.mediamate.model.flat.FlatService;
import com.mediamate.model.media_summary.detaile_summary.AdditionalCostSum;
import com.mediamate.model.media_summary.detaile_summary.ElectricityConsumption;
import com.mediamate.model.media_summary.detaile_summary.GasConsumption;
import com.mediamate.model.media_summary.detaile_summary.WaterConsumption;
import com.mediamate.model.meter.MeterService;
import com.mediamate.model.real_estate.RealEstate;
import com.mediamate.model.real_estate.RealEstateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MediaSummaryService {
    private RealEstateService realEstateService;
    private ElectricityConsumption electricityConsumption;
    private CostService costService;
    private MeterService meterService;
    private GasConsumption gasConsumption;
    private WaterConsumption waterConsumption;
    private AdditionalCostSum additionalCostSum;
    private FlatService flatService;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private List<Flat> flats = new ArrayList<>();
    private List<AdditionalCost> additionalCosts = new ArrayList<>();
    private MediaCost mediaCost = new MediaCost();
    @Autowired
    private MediaSummaryRepository mediaSummaryRepository;

    @Autowired
    public MediaSummaryService(RealEstateService realEstateService, AdditionalCostSum additionalCostSum, ElectricityConsumption electricityConsumption, CostService costService, MeterService meterService, GasConsumption gasConsumption, WaterConsumption waterConsumption, FlatService flatService) {
        this.realEstateService = realEstateService;
        this.additionalCostSum = additionalCostSum;
        this.electricityConsumption = electricityConsumption;
        this.gasConsumption = gasConsumption;
        this.waterConsumption = waterConsumption;
        this.costService = costService;
        this.meterService = meterService;
        this.flatService = flatService;
    }

    @Transactional
    public void createMediaSummaries(HttpSession httpSession) {
        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate = realEstateService.findById(realEstateId).orElseThrow();
        this.mediaCost = costService.findMediaCostByRealEstateIdInCurrentMonth(realEstateId, LocalDate.now());
        this.additionalCosts = costService.findAdditionalCostByRealEstateIdInCurrentMonth(realEstateId, LocalDate.now());
        this.flats = realEstate.getFlats();
        List<MediaSummary> mediaSummaries = createMediaSummaries();
        saveMediaSummaries(mediaSummaries);
    }

    private void saveMediaSummaries(List<MediaSummary> mediaSummaries) {
     mediaSummaryRepository.saveAll(mediaSummaries);
    }

    private double countSumGasPerRealEstateInGJ(List<com.mediamate.model.media_summary.MediaSummary> mediaSummaries) {
        double sumGasPerRealEstateInGJ = mediaSummaries.stream()
                .mapToDouble(mediaSummary -> mediaSummary.getGasConsumptionPerFlatInGJ().doubleValue())
                .sum();
        return sumGasPerRealEstateInGJ;
    }


    private List<MediaSummary> createMediaSummaries() {
        List<MediaSummary> mediaSummaries = this.flats.stream()
                .map(flat -> new MediaSummary(
                        LocalDate.now(),
                        flat,
                        electricityConsumption.countElectricityConsumption(flat.getId()),
                        gasConsumption.countGasConsumptionPerFlatInGJ(flat.getId()),
                        gasConsumption.countGasConsumptionPerRealEstateInM3(flat.getId()),
                        waterConsumption.countWaterConsumption(flat.getId()),
                        additionalCostSum.countTotalAdditionalCost(this.additionalCosts, flat.getRenter().getRenterCount(), flats.size())
                        ))
                .collect(Collectors.toList());

        double sumGasPerRealEstateInGJ = countSumGasPerRealEstateInGJ(mediaSummaries);
        int numberRentersInRealEstate = countRentersInRealEstate(mediaSummaries);

        mediaSummaries.stream().forEach(mediaSummary -> {
            mediaSummary.setGasConsumptionPerFlatInM3(gasConsumption.countConsumptionPerFlatInM3(sumGasPerRealEstateInGJ, numberRentersInRealEstate, mediaSummary, flats.size()));
            mediaSummary.setTotalElectricityCost(mediaSummary.getElectricityConsumptionInKW().doubleValue() * mediaCost.getElectricity());
            mediaSummary.setTotalGasCost(mediaSummary.getGasConsumptionPerFlatInM3().doubleValue() * mediaCost.getGas());
            mediaSummary.setTotalWaterCost(mediaSummary.getWaterConsumptionInM3().doubleValue() * mediaCost.getWater());
            mediaSummary.setSewarageCost(mediaSummary.getTotalWaterCost().doubleValue());
            mediaSummary.setTotalAllMediaCost(countTotalMediaCost(mediaSummary));
            mediaSummary.setElectricityUsagePercentage(countUsagePercentage(mediaSummary, mediaSummary.getTotalElectricityCost()));
            mediaSummary.setGasUsagePercentage(countUsagePercentage(mediaSummary, mediaSummary.getTotalGasCost()));
            mediaSummary.setWaterUsagePercentage(countUsagePercentage(mediaSummary, mediaSummary.getSewarageCost().add(mediaSummary.getTotalWaterCost())));
            mediaSummary.setAdditionalPercentage(countUsagePercentage(mediaSummary, mediaSummary.getTotalAdditionalCost()));
        });
        return mediaSummaries;
    }

    private int countRentersInRealEstate(List<MediaSummary> mediaSummaries) {
        return mediaSummaries.stream().mapToInt(mediaSummary -> mediaSummary.getFlat().getRenter().getRenterCount()).sum();
    }

    private double countTotalMediaCost(com.mediamate.model.media_summary.MediaSummary mediaSummary) {
        double electricityCost = mediaSummary.getTotalElectricityCost().doubleValue();
        double gasCost = mediaSummary.getTotalGasCost().doubleValue();
        double waterCost = mediaSummary.getTotalWaterCost().doubleValue();
        double additionalCost = mediaSummary.getTotalAdditionalCost().doubleValue();
        double sewarageCost = mediaSummary.getSewarageCost().doubleValue();
        return electricityCost + gasCost + waterCost + additionalCost + sewarageCost;
    }

    public List<TableAllFeesDto> findLastMediaSummariesLoginUser(HttpSession httpSession) {
        RealEstate realEstate = realEstateService.findRealEstateByHttpSession(httpSession);
        List <Flat> flats =  flatService.getFlatsInRealEstate(realEstate);
        List <MediaSummary> mediaSummaries = mediaSummaryRepository.findLastMediaSummariesByFlats(flats);
        List<TableAllFeesDto> TableAllFeesDtos = TableAlleFeesMapper.mapToTableAllFeesDtos(mediaSummaries);
        return TableAllFeesDtos;
    }
    public double countUsagePercentage(MediaSummary mediaSummary, BigDecimal mediaCost){
        BigDecimal totalCost = mediaSummary.getTotalAllMediaCost();
        BigDecimal percentage = mediaCost
                .multiply(new BigDecimal(100))
                .divide(totalCost, 3, RoundingMode.HALF_UP)
                .setScale(3, RoundingMode.HALF_UP);
        return percentage.doubleValue();
    }
}
