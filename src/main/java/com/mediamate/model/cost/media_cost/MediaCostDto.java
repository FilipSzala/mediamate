package com.mediamate.model.cost.media_cost;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MediaCostDto {
    private double electricityPrice;
    private double gasPrice;
    private double waterPrice;
}
