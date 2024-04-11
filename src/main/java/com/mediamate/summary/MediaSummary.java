package com.mediamate.summary;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class MediaSummary{
    private LocalDate createdAt;
    private double electricityConsumptionInKW;
    private double gasConsumptionPerFlatInGJ;
    private double gasConsumptionPerFlatInM3;
    private double gasConsumptionPerRealEstateInM3;
    private double waterConsumptionInM3;
    private double totalElectricityCost;
    private double totalGasCost;
    private double totalWaterCost;
    private double sewarageCost;
    private double totalAdditionalCost;
    private double totalAllMediaCost;

    public MediaSummary(LocalDate createdAt, double electricityConsumptionInKW, double gasConsumptionPerFlatInGJ,double gasConsumptionPerRealEstateInM3, double waterConsumptionInM3, double totalAdditionalCost) {
        this.createdAt = createdAt;
        this.electricityConsumptionInKW = electricityConsumptionInKW;
        this.gasConsumptionPerFlatInGJ = gasConsumptionPerFlatInGJ;
        this.gasConsumptionPerRealEstateInM3 = gasConsumptionPerRealEstateInM3;
        this.waterConsumptionInM3 = waterConsumptionInM3;
        this.totalAdditionalCost = totalAdditionalCost;
    }
}