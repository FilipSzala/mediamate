package com.mediamate.dto.dashboard.components.table;

import com.mediamate.model.media_summary.additional_cost.MediaSummaryAdditionalCostDto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class TableAdditionalFeesDto {
    private Long flatId;
    private List<MediaSummaryAdditionalCostDto> mediaSummaryAdditionalCostDtos;
    private BigDecimal sum;
}
