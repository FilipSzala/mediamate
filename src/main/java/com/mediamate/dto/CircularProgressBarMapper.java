package com.mediamate.dto;

import com.mediamate.model.media_summary.MediaSummary;

import java.util.List;
import java.util.stream.Collectors;

public class CircularProgressBarMapper {

    public static List<CircularProgressBarDto> mapToCircularProgressBarDtos(List<MediaSummary> mediaSummaries){
        return mediaSummaries.stream().map(mediaSummary -> mapToCircularProgressBarDto(mediaSummary)).collect(Collectors.toList());
    }

    private static CircularProgressBarDto mapToCircularProgressBarDto(MediaSummary mediaSummary) {
       return CircularProgressBarDto.builder()
                .additionalPercentage(mediaSummary.getAdditionalPercentage())
                .electricityUsagePercentage(mediaSummary.getElectricityUsagePercentage())
                .gasUsagePercentage(mediaSummary.getGasUsagePercentage())
                .waterUsagePercentage(mediaSummary.getWaterUsagePercentage())
                .build();
    }
}
