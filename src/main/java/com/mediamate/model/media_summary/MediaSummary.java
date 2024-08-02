package com.mediamate.model.media_summary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mediamate.model.flat.Flat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;


@Getter
@Setter
@Entity(name="MediaSummary")
@Table (name="media_summary")
public class MediaSummary{
    @Id
    @GeneratedValue (
            strategy = GenerationType.IDENTITY
    )
    @Column (
            name = "id",
            updatable = false
    )
    private Long id;
    private LocalDate createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (
            name = "flat_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey (name = "media_summary_flat_fk")
    )
    private Flat flat;
    @Column(name ="electricity_consumption_in_kw")
    private BigDecimal electricityConsumptionInKW;
    @Column(name = "gas_consumption_per_flat_in_gj")
    private BigDecimal gasConsumptionPerFlatInGJ;
    @Column(name = "gas_consumption_per_flat_in_m3")
    private BigDecimal gasConsumptionPerFlatInM3;
    @Column (name = "gas_consumption_per_realestate_in_m3")
    private BigDecimal gasConsumptionPerRealEstateInM3;
    @Column (name = "water_consumption_in_m3")
    private BigDecimal waterConsumptionInM3;
    private BigDecimal totalElectricityCost;
    private BigDecimal totalGasCost;
    private BigDecimal totalWaterCost;
    private BigDecimal sewarageCost;
    private BigDecimal totalAdditionalCost;
    private BigDecimal totalAllMediaCost;
    private double electricityUsagePercentage;
    private double gasUsagePercentage;
    private double waterUsagePercentage;
    private double additionalPercentage;
    public MediaSummary() {
    }

    public MediaSummary(LocalDate createdAt,Flat flat, double electricityConsumptionInKW, double gasConsumptionPerFlatInGJ, double gasConsumptionPerRealEstateInM3, double waterConsumptionInM3, double totalAdditionalCost) {
        this.flat = flat;
        this.createdAt = createdAt;
        this.electricityConsumptionInKW = new BigDecimal(electricityConsumptionInKW).setScale(2, RoundingMode.HALF_UP);
        this.gasConsumptionPerFlatInGJ = new BigDecimal(gasConsumptionPerFlatInGJ).setScale(2, RoundingMode.HALF_UP);
        this.gasConsumptionPerRealEstateInM3 = new BigDecimal(gasConsumptionPerRealEstateInM3).setScale(2, RoundingMode.HALF_UP);
        this.waterConsumptionInM3 = new BigDecimal(waterConsumptionInM3).setScale(2, RoundingMode.HALF_UP);
        this.totalAdditionalCost = new BigDecimal(totalAdditionalCost).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getGasConsumptionPerFlatInM3() {
        return gasConsumptionPerFlatInM3;
    }

    public void setGasConsumptionPerFlatInM3(double gasConsumptionPerFlatInM3) {
        this.gasConsumptionPerFlatInM3 = new BigDecimal(gasConsumptionPerFlatInM3).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalElectricityCost() {
        return totalElectricityCost;
    }

    public void setTotalElectricityCost(double totalElectricityCost) {
        this.totalElectricityCost = new BigDecimal(totalElectricityCost).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalGasCost() {
        return totalGasCost;
    }

    public void setTotalGasCost(double totalGasCost) {
        this.totalGasCost = new BigDecimal(totalGasCost).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalWaterCost() {
        return totalWaterCost;
    }

    public void setTotalWaterCost(double totalWaterCost) {
        this.totalWaterCost = new BigDecimal(totalWaterCost).setScale(2,RoundingMode.HALF_UP);
    }

    public BigDecimal getSewarageCost() {
        return sewarageCost;
    }

    public void setSewarageCost(double sewarageCost) {
        this.sewarageCost = new BigDecimal(sewarageCost).setScale(2,RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalAllMediaCost() {
        return totalAllMediaCost;
    }

    public void setTotalAllMediaCost(double totalAllMediaCost) {
        this.totalAllMediaCost = new BigDecimal(totalAllMediaCost).setScale(2,RoundingMode.HALF_UP);
    }


}