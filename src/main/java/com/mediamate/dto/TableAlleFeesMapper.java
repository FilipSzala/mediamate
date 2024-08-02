package com.mediamate.dto;
import com.mediamate.model.media_summary.MediaSummary;

import java.util.List;
import java.util.stream.Collectors;

public class TableAlleFeesMapper {

    public static List<TableAllFeesDto> mapToTableAllFeesDtos(List<MediaSummary> mediaSummaries) {
        return mediaSummaries.stream()
                .map(mediaSummary -> mapToAllFeesDto(mediaSummary))
                .collect(Collectors.toList());
    }

    private static TableAllFeesDto mapToAllFeesDto(MediaSummary mediaSummary) {
        return TableAllFeesDto.builder()
                .id(mediaSummary.getId())
                .createdAt(mediaSummary.getCreatedAt())
                .flatId(mediaSummary.getFlat().getId())
                .totalElectricityCost(mediaSummary.getTotalElectricityCost())
                .totalGasCost(mediaSummary.getTotalGasCost())
                .totalWaterCost(mediaSummary.getTotalWaterCost())
                .sewarageCost(mediaSummary.getSewarageCost())
                .totalAdditionalCost(mediaSummary.getTotalAdditionalCost())
                .totalAllMediaCost(mediaSummary.getTotalAllMediaCost())
                .build();
    }

}
