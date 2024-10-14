package com.mediamate.dto.dashboard.components.table;
import com.mediamate.model.media_summary.MediaSummary;
import com.mediamate.model.meter.Meter;
import com.mediamate.model.meter.MeterType;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class TableWaterDtoMapper {
    private static final BigDecimal MULTIPLIER_TWO = BigDecimal.valueOf(2);

    public static List<TableWaterDto> mapToTableWaterDtos(List<MediaSummary> mediaSummaries, List<Meter> meters) {
        return mediaSummaries.stream()
                .map(mediaSummary -> {
                    List<Meter> metersWithTheSameMediaSummaryFlatId  = meters.stream()
                            .filter(meter -> meter.getFlat().getId().equals(mediaSummary.getFlat().getId()))
                            .collect(Collectors.toList());
                    return mapToTableWaterDto(mediaSummary,metersWithTheSameMediaSummaryFlatId,mediaSummaries.size());
                })
                .collect(Collectors.toList());
    }

    private static TableWaterDto mapToTableWaterDto(MediaSummary mediaSummary,List<Meter> meters, int flatNumber) {
        Meter coldWater = meters.stream().filter(meter -> meter.getMeterType()==MeterType.COLD_WATER).findAny().orElse(new Meter((double)0));
        Meter warmWater = meters.stream().filter(meter -> meter.getMeterType()==MeterType.HOT_WATER).findAny().orElse(new Meter((double)0));
        return TableWaterDto.builder()
                .flatId(mediaSummary.getFlat().getId())
                .coldWaterMeterReadingPerFlat(coldWater.getValue())
                .warmWaterMeterReadingPerFlat(warmWater.getValue())
                .usageWaterWithSewage(mediaSummary.getWaterConsumptionInM3().multiply(MULTIPLIER_TWO))
                .usageColdAndWarmWater(mediaSummary.getWaterConsumptionInM3())
                .administrationWaterPerFlat(mediaSummary.getWaterAdministrationCost())
                .administrationWaterPerRealEstate(mediaSummary.getWaterAdministrationCost().multiply(BigDecimal.valueOf(flatNumber)))
                .sumInZl((mediaSummary.getTotalWaterCost()))
                .build();
    }
}
