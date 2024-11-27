package com.mediamate.controller.settlement.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class GasPriceWithDistribution {
    private double administrationPriceGross;
    private double priceZlPerM3WithoutDistribution;
}
