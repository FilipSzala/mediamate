package com.mediamate.realestate;

import com.mediamate.user.role.owner.Owner;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RealEstateDto {
    private Long id;
    private Owner owner;
    private String address;
}
