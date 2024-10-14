package com.mediamate.model.media_summary.additional_cost;

import com.mediamate.model.media_summary.MediaSummary;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class MediaSummaryAdditionalCostDto {
    private Long id;
    private String name;
    private BigDecimal value;
}
