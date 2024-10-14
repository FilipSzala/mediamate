package com.mediamate.dto.header.components;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RealEstateAddressesDto {
    private Long realEstateId;
    private String address;
}
