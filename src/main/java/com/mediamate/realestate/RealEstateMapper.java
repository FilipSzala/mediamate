package com.mediamate.realestate;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class RealEstateMapper {
    public static List<RealEstateDto> mapToRealEstateDtos (List <RealEstate> realEstates){
        return realEstates.stream()
                .map(realEstate -> mapToRealEstateDto(realEstate))
                .collect(Collectors.toList());
    }
    private static RealEstateDto mapToRealEstateDto (RealEstate realEstate){
        return RealEstateDto.builder()
                .id(realEstate.getId())
                .address(realEstate.getAddress())
                .build();
    }
}
