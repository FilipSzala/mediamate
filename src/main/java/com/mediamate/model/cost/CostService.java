package com.mediamate.model.cost;

import com.mediamate.model.cost.additionalCost.AdditionalCost;
import com.mediamate.model.cost.media_cost.MediaCost;
import com.mediamate.model.real_estate.RealEstate;
import com.mediamate.model.real_estate.RealEstateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CostService {
    @Autowired
    private CostRepository costRepository;
    @Autowired
    private RealEstateService realEstateService;

    public MediaCost findMediaCostByRealEstateIdInCurrentMonth(Long realEstateId, LocalDate localDate){
        LocalDate firstDay = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDay =  localDate.with(TemporalAdjusters.lastDayOfMonth());
        MediaCost mediaCost = (MediaCost) costRepository.findMediaCostByRealEstateIdAndCostTypeInCurrentMonth(realEstateId,firstDay,lastDay).orElseThrow();
        return mediaCost;
    }

    public List <AdditionalCost> findAdditionalCostByRealEstateIdInCurrentMonth(Long realEstateId, LocalDate localDate){
        LocalDate firstDay = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDay =  localDate.with(TemporalAdjusters.lastDayOfMonth());
        List <AdditionalCost> additionalCosts =  costRepository.findAdditionalCostByRealEstateIdAndCostTypeAndDate(realEstateId,firstDay,lastDay);
        return additionalCosts;
    }
    public List <Cost> findAdditionalCostByRealEstateIdInLastMonth(HttpSession httpSession, LocalDate localDate){
        Long realEstateId =(Long) httpSession.getAttribute("chosenRealEstateId");
        LocalDate firstDay = localDate.with(TemporalAdjusters.firstDayOfMonth()).minusMonths(1L);
        LocalDate lastDay =  localDate.with(TemporalAdjusters.lastDayOfMonth()).minusMonths(1L);
        List <Cost> additionalCosts =  costRepository.findAdditionalCostsInLastMonth(realEstateId,firstDay,lastDay);
        return additionalCosts;
    }
    public Cost findLastMediaCost(HttpSession httpSession) {
            Long realEstateId =(Long) httpSession.getAttribute("chosenRealEstateId");
            // TODO: Remember to change the method findMediaCostDesc to  method that will return only the last record from the media.
            List <Cost> costs = costRepository.findMediaCostByDateDesc(realEstateId);
            return costs.isEmpty()? new MediaCost():costs.get(0);
    }

    @Transactional
    public void createMediaCost(MediaCost mediaCost, HttpSession httpSession){
        Cost cost = mediaCost;
        Long realEstateId =(Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate =realEstateService.findById(realEstateId).orElseThrow();
        realEstate.addCost(cost);
        realEstateService.updateRealEstate(realEstate);
    }
    @Transactional
    public void createAdditionalCosts(List<AdditionalCost> additionalCosts,HttpSession httpSession){
        List<Cost> costs = additionalCosts.stream()
                .map(additionalCost -> {
                    additionalCost.setCreatedAt(LocalDate.now());
                    return (Cost) additionalCost;
                })
                .collect(Collectors.toList());
        Long realEstateId =(Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate =realEstateService.findById(realEstateId).orElseThrow();
        realEstate.addCosts(costs);
        realEstateService.updateRealEstate(realEstate);
    }
    public List <AdditionalCost> findLatestAdditionalCostByRealEstateId(Long realEstateId){
        List <Cost> costs = costRepository.findLatestAdditionalCostByRealEstateId(realEstateId);
        List <AdditionalCost> additionalCosts = costs.stream().map(cost -> (AdditionalCost) cost).collect(Collectors.toList());
        return additionalCosts;
    }

    public List<MediaCost> findMediaCostByRealEstateIdInCurrentYear(Long realEstateId){
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endDate = LocalDate.now();
       List<MediaCost> mediaCosts = costRepository.findMediaCostByRealEstateIdAndDate(realEstateId, startDate,endDate);
       return mediaCosts;
    }
}
