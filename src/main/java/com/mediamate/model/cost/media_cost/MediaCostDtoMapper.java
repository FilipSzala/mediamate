package com.mediamate.model.cost.media_cost;

public class MediaCostDtoMapper {
    public static MediaCostDto mapToMediaCostDto (MediaCost cost){
        return MediaCostDto.builder()
                .electricityPrice(cost.getElectricity())
                .gasPrice(cost.getGas())
                .waterPrice(cost.getWater())
                .build();
    }
}
