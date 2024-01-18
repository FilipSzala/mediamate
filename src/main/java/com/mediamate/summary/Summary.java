package com.mediamate.summary;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class Summary {
    private String rentersName;
    private Double waterPrice;
    private Double electricityPrice;
    private Double gasPrice;
    private Double additionalPrice;
    private Double sum;
}