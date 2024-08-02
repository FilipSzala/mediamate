package com.mediamate.model.real_estate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RealEstateDto {
    private Long id;
    private String address;
}
