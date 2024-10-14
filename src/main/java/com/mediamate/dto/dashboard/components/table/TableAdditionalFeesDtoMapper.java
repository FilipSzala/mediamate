package com.mediamate.dto.dashboard.components.table;

import com.mediamate.model.media_summary.MediaSummary;
import com.mediamate.model.media_summary.additional_cost.MediaSummaryAdditionalCostDtoMapper;

public class TableAdditionalFeesDtoMapper {
    public static TableAdditionalFeesDto mapToAdditionalFeesDtoMapper (MediaSummary mediaSummary){
        return TableAdditionalFeesDto.builder()
                .mediaSummaryAdditionalCostDtos(MediaSummaryAdditionalCostDtoMapper.mapToMediaSummarryAdditionalCostDtos(mediaSummary.getMediaSummaryAdditionalCosts()))
                .flatId(mediaSummary.getFlat().getId())
                .sum(mediaSummary.getTotalAdditionalCost())
                .build();

    }
}
