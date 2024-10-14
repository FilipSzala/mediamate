package com.mediamate.dto.dashboard.components.circular_progress;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CircularProgressBarDto {
    private double electricityUsagePercentage;
    private double gasUsagePercentage;
    private double waterUsagePercentage;
    private double additionalPercentage;
}
