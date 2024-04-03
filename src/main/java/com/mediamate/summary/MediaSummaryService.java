package com.mediamate.summary;

import com.mediamate.cost.CostService;
import com.mediamate.cost.additionalCost.AdditionalCost;
import com.mediamate.cost.additionalCost.ChargeType;
import com.mediamate.cost.mediaCost.MediaCost;
import com.mediamate.flat.Flat;
import com.mediamate.meter.Meter;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateService;
import com.mediamate.summary.detaile_summary.AdditionalCostSummaryService;
import com.mediamate.summary.detaile_summary.ElectricitySummaryService;
import com.mediamate.summary.detaile_summary.GasSummaryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MediaSummaryService{
    private Meter lastMeterInFlat;
    private Meter oneBeforeLastMeterInFlat;
    private Meter lastMeterInRealEstate;
    private Meter oneBeforeLastMeterInRealEstate;
    private MediaCost mediaCost;
    private int flatCount;


    private AdditionalCostSummaryService additionalCostSummaryService;
    private RealEstateService realEstateService;
    private CostService costService;
    private ElectricitySummaryService electricitySummaryService;
    private GasSummaryService gasSummaryService;
    @Autowired
    public MediaSummaryService(RealEstateService realEstateService, CostService costService ,ElectricitySummaryService electricitySummaryService,GasSummaryService gasSummaryService, AdditionalCostSummaryService additionalCostSummaryService) {
        this.realEstateService = realEstateService;
        this.costService = costService;
        this.electricitySummaryService = electricitySummaryService;
        this.gasSummaryService = gasSummaryService;
        this.additionalCostSummaryService = additionalCostSummaryService;
    }

/*    public List<MediaSummary> generateFlatSummaries(HttpSession httpSession, LocalDate date){

        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate =realEstateService.findById(realEstateId).get();
        Cost cost = costService.getCostByRealEstateIdAndDate(realEstateId,date).get();
        List <Flat> flats = realEstate.getFlats();
        if(Cost )
        mediaCost = cost.getMediaCost();
        lastMeterInRealEstate = setLastMeterInRealEstate(realEstate);
        oneBeforeLastMeterInRealEstate = setOneBeforeLastMeterInRealEstate(realEstate);
        flatCount = realEstate.getFlats().size();
        List <AdditionalCost> additionalCosts = cost.getAdditionalCosts();
        List <AdditionalCost> additionalCosts =

        List<MediaSummary> summaries = new ArrayList<>();


        summaries = createSummaries(realEstate,cost, flats, additionalCosts);
        *//*setSum(summaries);*//*

        return summaries;
    }*/
/*    private List<MediaSummary> createSummaries(RealEstate realEstate,Cost cost,  List<Flat> flats,List<AdditionalCost> additionalCosts) {
        List<MediaSummary> summaries;
        summaries = flats.stream()
                .map(flat -> {
                    setLastMeter(flat);
                    setOneBeforeLastMeterInFlat(flat);


                    return MediaSummary.builder()
                            .createdAt(LocalDate.now())
                            .rentersName(flat.getRentersFullName())
                            .electricitySummary(electricitySummaryService.createElectricity(mediaCost,lastMeterInFlat,oneBeforeLastMeterInFlat,lastMeterInRealEstate,oneBeforeLastMeterInRealEstate,flatCount))
                            .gasSummary(gasSummaryService.createGasSummary(flats,mediaCost,lastMeterInFlat,oneBeforeLastMeterInFlat,lastMeterInRealEstate,oneBeforeLastMeterInRealEstate,flatCount))
                            .waterSummary(waterSummaryService.createWaterSummary(mediaCost,lastMeterInFlat,oneBeforeLastMeterInFlat,lastMeterInRealEstate,oneBeforeLastMeterInRealEstate,flatCount))
                            .additionalCostSummaries(additionalCostSummaryService.createAdditionalCostsSummary(additionalCosts,flatCount))
                            .totalAdditionalCost(setAdditionalPrice(cost,flat.getRenterCount()))
                            .build();
                })
                .collect(Collectors.toList());
        return summaries;
    }*/
  /*  private void setSum(List<MediaSummary> summaries) {
        summaries.stream().forEach(mediaSummary -> mediaSummary.setTotalMediaSumByFlat(mediaSummary.getWaterTotalPriceForFlat().add(mediaSummary.getValueTotalPriceForFlat().add(mediaSummary.getValueTotalPriceForFlat().add(mediaSummary.getAdditionalPrice()))).setScale(2,RoundingMode.UP)));
    }

*/


    private Meter setLastMeterInRealEstate(RealEstate realEstate) {
        return  realEstate.getMeters().get(realEstate.getMeters().size()-1);
    }
    private Meter setOneBeforeLastMeterInRealEstate(RealEstate realEstate) {
        return  realEstate.getMeters().get(realEstate.getMeters().size()-2);
    }

/*    private BigDecimal setAdditionalPrice (Cost cost,int renterCount){
        List<AdditionalCost> additionalCosts = cost.getAdditionalCosts();
        Double sum = additionalCosts.stream()
                .mapToDouble(additionalCost -> countAdditionalCost(additionalCost,renterCount))
                .sum();
        BigDecimal totalAdditionalCost = new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP);
        return totalAdditionalCost;
    }*/
    private double countAdditionalCost(AdditionalCost additionalCost, int renterCount){
        ChargeType chargeType =additionalCost.getChargeType();
        int billingTimePeriod =additionalCost.getTimePeriod().getValue();
        Double additionalCostAmount = additionalCost.getPrice();

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
