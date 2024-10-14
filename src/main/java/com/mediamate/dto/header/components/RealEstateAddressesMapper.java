package com.mediamate.dto.header.components;

import com.mediamate.model.real_estate.RealEstateDto;

import java.util.List;
import java.util.stream.Collectors;

public class RealEstateAddressesMapper {
    public static List<RealEstateAddressesDto> mapToRealEstateAddressesDtos(List<RealEstateDto> realEstates){
        return realEstates.stream().map(realEstate -> mapToRealEstateAddressesDto(realEstate)).collect(Collectors.toList());
    }

    private static RealEstateAddressesDto mapToRealEstateAddressesDto(RealEstateDto realEstate) {
        return RealEstateAddressesDto.builder()
                .realEstateId(realEstate.getId())
                .address(realEstate.getAddress())
                .build();
    }
}
