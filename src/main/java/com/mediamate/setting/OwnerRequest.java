package com.mediamate.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OwnerRequest {
    private String firstName;
    private String lastName;
    private int realEstateCount;
}
