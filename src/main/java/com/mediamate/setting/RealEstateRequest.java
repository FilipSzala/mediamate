package com.mediamate.setting;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RealEstateRequest {
    private String address;
    private int flatCount;
}
