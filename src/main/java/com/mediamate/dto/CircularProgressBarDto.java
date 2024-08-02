package com.mediamate.dto;

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
