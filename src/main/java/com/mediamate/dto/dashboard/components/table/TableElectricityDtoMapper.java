package com.mediamate.dto.dashboard.components.table;

import com.mediamate.model.media_summary.MediaSummary;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;



public class TableElectricityDtoMapper {
    public static List<TableElectricityDto> mapToTableElectricityDtos(List<MediaSummary> mediaSummaries) {
        return mediaSummaries.stream()
                .map(mediaSummary -> mapToElectricityDto(mediaSummary,mediaSummaries.size()))
                .collect(Collectors.toList());
    }

    private static TableElectricityDto mapToElectricityDto(MediaSummary mediaSummary, int flatCount) {
        return TableElectricityDto.builder()
                .flatId(mediaSummary.getFlat().getId())
                .meterReadingPerFlat(roundNumber(mediaSummary.getElectricityMeterReadingPerFlatInKW()))
                .usageWithoutAdministrationPerFlat(roundNumber(mediaSummary.getElectricityConsumptionInKW()))
                .usageIncludingAdministrationPerFlat(roundNumber(
                        mediaSummary.getElectricityConsumptionInKW().add(mediaSummary.getElectricityAdministrationConsumptionInKW())))
                .usagePerAdministration(roundNumber(
                        mediaSummary.getElectricityAdministrationConsumptionInKW().multiply(new BigDecimal(flatCount))))
                .meterReadingPerAdministration(roundNumber(mediaSummary.getElectricityMeterReadingPerAdministrationInKW()))
                .priceAdministration(roundNumber(mediaSummary.getElectricityPricePerAdministration().multiply(new BigDecimal(flatCount))))
                .sum(roundNumber(mediaSummary.getTotalElectricityCost()))
                .build();
    }

    private static BigDecimal roundNumber(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
