package com.mediamate.model.media_summary.additional_cost;

import java.util.List;
import java.util.stream.Collectors;

public class MediaSummaryAdditionalCostDtoMapper {
    public static List<MediaSummaryAdditionalCostDto> mapToMediaSummarryAdditionalCostDtos (List<MediaSummaryAdditionalCost> costs){
       return costs.stream().filter(cost -> !cost.getName().equals("Woda administracja")).map(cost -> mapToMediaSummaryAdditionalCostDto(cost)).collect(Collectors.toList());
    }

    private static MediaSummaryAdditionalCostDto mapToMediaSummaryAdditionalCostDto(MediaSummaryAdditionalCost cost) {
        return MediaSummaryAdditionalCostDto.builder()
                .id(cost.getId())
                .name(cost.getName())
                .value(cost.getValue())
                .build();
    }
}
