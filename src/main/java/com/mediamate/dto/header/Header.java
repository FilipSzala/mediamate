package com.mediamate.dto.header;

import com.mediamate.dto.header.components.RealEstateAddressesDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Header {
    private List<RealEstateAddressesDto> realEstateAddressesDtos;
    private String chosenRealEstateAddress;

    public Header(String chosenRealEstateAddress) {
        this.chosenRealEstateAddress = chosenRealEstateAddress;
    }
}
