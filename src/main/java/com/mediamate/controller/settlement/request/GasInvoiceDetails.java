package com.mediamate.controller.settlement.request;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class GasInvoiceDetails {
    private double totalSumGross;
    private double subscriptionFeeNet;
    private double fixedDistributionNet;
    private double vatRate;
    private double gasConsumptionInM3;
}
