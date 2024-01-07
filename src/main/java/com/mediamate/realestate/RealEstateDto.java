package com.mediamate.realestate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RealEstateDto {
    private Long id;
    private Long ownerId;
    private String address;
}
