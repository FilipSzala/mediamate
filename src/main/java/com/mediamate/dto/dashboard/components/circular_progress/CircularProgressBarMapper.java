package com.mediamate.dto.dashboard.components.circular_progress;

import com.mediamate.model.media_summary.MediaSummary;

import java.util.List;
import java.util.stream.Collectors;

public class CircularProgressBarMapper {

    public static CircularProgressBarDto mapToCircularProgressBarDto(MediaSummary mediaSummary) {
       return CircularProgressBarDto.builder()
                .additionalPercentage(mediaSummary.getAdditionalPercentage())
                .electricityUsagePercentage(mediaSummary.getElectricityUsagePercentage())
                .gasUsagePercentage(mediaSummary.getGasUsagePercentage())
                .waterUsagePercentage(mediaSummary.getWaterUsagePercentage())
                .build();
    }
}
