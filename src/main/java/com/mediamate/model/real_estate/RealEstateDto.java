package com.mediamate.model.realestate;

import com.mediamate.model.user.role.owner.Owner;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RealEstateDto {
    private Long id;
    private Owner owner;
    private String address;
}
