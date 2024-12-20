package com.mediamate.controller.dashboard;

import com.mediamate.controller.dashboard.sms_api.SmsSenderService;
import com.mediamate.dto.dashboard.Dashboard;
import com.mediamate.dto.dashboard.components.bar_chart.BarChart;
import com.mediamate.dto.dashboard.components.bar_chart.BarChartType;
import com.mediamate.dto.dashboard.components.circular_progress.CircularProgressBarDto;
import com.mediamate.dto.dashboard.components.circular_progress.CircularProgressBarMapper;
import com.mediamate.dto.dashboard.components.table.*;
import com.mediamate.dto.header.Header;
import com.mediamate.dto.header.components.RealEstateAddressesDto;
import com.mediamate.dto.header.components.RealEstateAddressesMapper;
import com.mediamate.model.cost.CostService;
import com.mediamate.model.cost.additionalCost.AdditionalCost;
import com.mediamate.model.cost.media_cost.MediaCost;
import com.mediamate.model.cost.media_cost.MediaCostDtoMapper;
import com.mediamate.model.flat.Flat;
import com.mediamate.model.flat.FlatService;
import com.mediamate.model.media_summary.MediaSummary;
import com.mediamate.model.media_summary.MediaSummaryService;
import com.mediamate.model.meter.Meter;
import com.mediamate.model.meter.MeterService;
import com.mediamate.model.real_estate.RealEstate;
import com.mediamate.model.real_estate.RealEstateService;
import com.mediamate.model.renter.Renter;
import com.mediamate.model.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@NoArgsConstructor
public class DashboardService {
    private RealEstateService realEstateService;
    private FlatService flatService;
    private MediaSummaryService mediaSummaryService;
    private UserService userService;
    private MeterService meterService;
    private CostService costService;
    private SmsSenderService smsSenderService;

    @Autowired
    public DashboardService(RealEstateService realEstateService, FlatService flatService, MediaSummaryService mediaSummaryService,UserService userService, MeterService meterService,CostService costService, SmsSenderService smsSenderService) {
        this.realEstateService = realEstateService;
        this.flatService = flatService;
        this.mediaSummaryService = mediaSummaryService;
        this.userService = userService;
        this.meterService = meterService;
        this.costService = costService;
        this.smsSenderService = smsSenderService;
    }

    public Dashboard findDashboardDataForLoginAccount(HttpSession httpSession) {
        RealEstate realEstate = realEstateService.findRealEstateByHttpSession(httpSession);
        List <Flat> flats =  flatService.getFlatsInRealEstate(realEstate);
        List <AdditionalCost> additionalCosts = costService.findLatestAdditionalCostByRealEstateId(realEstate.getId());
        List <MediaSummary> mediaSummaries = mediaSummaryService.findLastMediaSummariesByFlats(flats);
        List <TableAllFeesDto> tableAllFeesDtos = TableAlleFeesMapper.mapToTableAllFeesDtos(mediaSummaries);
        Dashboard dashboard = new Dashboard(tableAllFeesDtos);
        if(userService.isUserRole().equals(true) & !mediaSummaries.isEmpty()){
            Long flatId = userService.getFlatIdForLoginUser();
            dashboard.setFlatId(flatId);
            setElectricityData(mediaSummaries, dashboard);
            setMediaCostData(httpSession, dashboard);
            setWaterData(mediaSummaries,flats,dashboard);
            setGasData(mediaSummaries,dashboard);
            setAdditionalFeesData(mediaSummaries,additionalCosts,dashboard);
            setCircularProgressBarData(mediaSummaries,flatId,dashboard);
            setBarCharts(realEstate.getId(),flatId,dashboard);
            setTableAllFeesDto(mediaSummaries,dashboard);
        }
        else {
            setRealEstateAddressesForOwner(dashboard);
        }
        return dashboard;
    }

    private void setCircularProgressBarData(List<MediaSummary> mediaSummaries, Long flatId, Dashboard dashboard) {
        MediaSummary mediaSummary = mediaSummaries.stream().filter(media -> media.getFlat().getId()==flatId).findFirst().orElseThrow();
        CircularProgressBarDto circularProgressBarDto = CircularProgressBarMapper.mapToCircularProgressBarDto(mediaSummary);
        dashboard.setCircularProgressBarDto(circularProgressBarDto);
    }

    private void setTableAllFeesDto(List<MediaSummary> mediaSummaries,Dashboard dashboard) {
        List <TableAllFeesDto> tableAllFeesDtos = TableAlleFeesMapper.mapToTableAllFeesDtos(mediaSummaries);
        dashboard.setTableAllFeesDtos(tableAllFeesDtos);
    }

    private void setBarCharts(Long realEstateId,Long flatId, Dashboard dashboard) {
        List<MediaCost> mediaCostInCurrentYear = costService.findMediaCostByRealEstateIdInCurrentYear(realEstateId);
        List<BarChart> barCharts = mediaCostInCurrentYear.stream()
                .flatMap(cost -> Stream.of(
                        new BarChart(cost.getCreatedAt(), cost.getElectricity(), BarChartType.ELECTRICITY),
                        new BarChart(cost.getCreatedAt(), cost.getWater(), BarChartType.WATER),
                        new BarChart(cost.getCreatedAt(), cost.getGas(), BarChartType.GAS)
                ))
                .collect(Collectors.toList());
        List <MediaSummary> mediaSummaries = mediaSummaryService.findMediaSummariesByFlatThisYear(flatId);
                mediaSummaries.stream().forEach(mediaSummary -> {
                    BarChart barChartForAllMedia = new BarChart(mediaSummary.getCreatedAt(),mediaSummary.getTotalAllMediaCost().doubleValue() , BarChartType.ALL_MEDIA);
                    BarChart barChartForAdditionalFees = new BarChart(mediaSummary.getCreatedAt(),mediaSummary.getTotalAdditionalCost().doubleValue() , BarChartType.ADDITIONAL_FEES);
                    barCharts.add(barChartForAllMedia);
                    barCharts.add(barChartForAdditionalFees);
                });
        dashboard.setBarCharts(barCharts);
    }

    private void setAdditionalFeesData(List<MediaSummary> mediaSummaries,List <AdditionalCost> additionalCosts, Dashboard dashboard) {
        List <TableAdditionalFeesDto> costs = mediaSummaries.stream().map(mediaSummary -> TableAdditionalFeesDtoMapper.mapToAdditionalFeesDtoMapper(mediaSummary)).collect(Collectors.toList());
        List<AdditionalCost> additionalCostWithouWater =  additionalCosts.stream().filter(cost -> !cost.getName().equals("Woda administracja")).collect(Collectors.toList());
        dashboard.setAdditionalCosts(additionalCostWithouWater);
        dashboard.setTableAdditionalFees(costs);
    }

    private void setGasData(List<MediaSummary> mediaSummaries, Dashboard dashboard) {
        List <TableGasDto> tableGasDtos = TableGasDtoMapper.mapToTableGasDtos(mediaSummaries);
        dashboard.setTableGasDtos(tableGasDtos);
    }

    private void setWaterData(List<MediaSummary> mediaSummaries, List<Flat> flats,Dashboard dashboard) {
        List<Long> flatIds = flats.stream().map(flat->flat.getId()).collect(Collectors.toList());
        List<Meter> meters = meterService.findLastMetersByFlatIdsInLastMonthForColdAndWarmWater(flatIds);
        List<TableWaterDto> tableWaterDtos = TableWaterDtoMapper.mapToTableWaterDtos(mediaSummaries, meters);
        dashboard.setTableWaterDtos(tableWaterDtos);
    }

    private void setRealEstateAddressesForOwner(Dashboard dashboard) {
        List<RealEstateAddressesDto> realEstateAddressesDtos = RealEstateAddressesMapper.mapToRealEstateAddressesDtos(realEstateService.findAllByLogInUser());
        dashboard.setRealEstateAddressesDtos(realEstateAddressesDtos);
    }
    private void setMediaCostData(HttpSession httpSession, Dashboard dashboard) {
        MediaCost cost = (MediaCost)costService.findLastMediaCost(httpSession);
        dashboard.setMediaCostDto(MediaCostDtoMapper.mapToMediaCostDto(cost));
    }

    private void setElectricityData(List<MediaSummary> mediaSummaries, Dashboard dashboard) {
        List <TableElectricityDto> tableElectricityDtos = TableElectricityDtoMapper.mapToTableElectricityDtos(mediaSummaries);
        dashboard.setTableElectricityDtos(tableElectricityDtos);
    }

    public Header findHeaderData(HttpSession httpSession) {
        String chosenAddress = realEstateService.findRealEstateByHttpSession(httpSession).getAddress();
        Header header = new Header(chosenAddress);
        if(userService.isUserRole().equals(false)){
            List<RealEstateAddressesDto> realEstateAddressesDtos = RealEstateAddressesMapper.mapToRealEstateAddressesDtos(realEstateService.findAllByLogInUser());
            header.setRealEstateAddressesDtos(realEstateAddressesDtos);
        }
        return header;
    }

    public void sendSmsWithMediaSummary(HttpSession httpSession) {
        RealEstate realEstate = realEstateService.findRealEstateByHttpSession(httpSession);
        List<Flat> flats = flatService.getFlatsInRealEstate(realEstate);
        List<MediaSummary> mediaSummaries = mediaSummaryService.findLastMediaSummariesByFlats(flats);
        Boolean ifMediaSummariesIsSameSizeFlat = flats.size() == mediaSummaries.size() ? true : false;
        if (ifMediaSummariesIsSameSizeFlat) {
            flats.forEach(flat -> {
                boolean hasRenterPhoneNumber = flat.getRenter() != null && flat.getRenter().getPhoneNumber() != null;
                if (hasRenterPhoneNumber) {
                    String phoneNumber = flat.getRenter().getPhoneNumber();
                    Renter renter = flat.getRenter();
                    MediaSummary mediaSummary = findMediaSummaryByFlat(mediaSummaries, flat);
                    BigDecimal mediaTotalCost = mediaSummary.getTotalAllMediaCost() != null ? mediaSummary.getTotalAllMediaCost() : BigDecimal.ZERO;
                    smsSenderService.sendSmsIfValid(renter,phoneNumber, mediaTotalCost);
                }
            });
        }
        else {
            throw new IllegalStateException("The number of flats does not match the number of media summaries. Cannot send SMS.");
        }
    }

    private MediaSummary findMediaSummaryByFlat(List<MediaSummary> mediaSummaries, Flat flat) {
        MediaSummary mediaSummary = mediaSummaries.stream()
                .filter(media -> media.getFlat() != null &&
                        media.getFlat().getId() != null &&
                        media.getFlat().getId().equals(flat.getId()))
                .findFirst()
                .orElse(new MediaSummary());
        return mediaSummary;
    }


}
