package com.mediamate.dto.dashboard.components.table;

import com.mediamate.model.media_summary.MediaSummary;

import java.util.List;
import java.util.stream.Collectors;

public class TableGasDtoMapper {
    public static List<TableGasDto> mapToTableGasDtos (List<MediaSummary> mediaSummaries){
        return  mediaSummaries.stream().map(mediaSummary -> mapToTableGasDto(mediaSummary)).collect(Collectors.toList());
    }

    private static TableGasDto mapToTableGasDto(MediaSummary mediaSummary) {
        return TableGasDto.builder()
                .flatId(mediaSummary.getFlat().getId())
                .meterReading(mediaSummary.getGasMeterReadingPerFlat())
                .usageInGj(mediaSummary.getGasConsumptionPerFlatInGJ())
                .usageInM3(mediaSummary.getGasConsumptionPerFlatInM3())
                .sumInZl(mediaSummary.getTotalGasCost())
                .build();
    }
}
