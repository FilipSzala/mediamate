package com.mediamate.model.media_summary;

import com.mediamate.model.flat.Flat;
import com.mediamate.model.media_summary.additional_cost.MediaSummaryAdditionalCost;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity(name="MediaSummary")
@Table (name="media_summary")
public class MediaSummary{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToMany(mappedBy = "mediaSummary",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<MediaSummaryAdditionalCost> mediaSummaryAdditionalCosts = new ArrayList<>();
    @Column(name ="electricity_consumption_in_kw")
    private BigDecimal electricityConsumptionInKW;
    @Column (name = "electricity_administration_consumption_in_kw")
    private BigDecimal electricityAdministrationConsumptionInKW;
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
    private BigDecimal waterAdministrationCost;
    private BigDecimal sewarageCost;
    private BigDecimal totalAdditionalCost;
    private BigDecimal totalAllMediaCost;
    private Double electricityUsagePercentage;
    private Double gasUsagePercentage;
    private Double waterUsagePercentage;
    private Double additionalPercentage;
    @Column (name = "electricity_meter_reading_per_flat_in_kw")
    private BigDecimal electricityMeterReadingPerFlatInKW;
    @Column (name = "electricity_meter_reading_per_administration_in_kw")
    private BigDecimal electricityMeterReadingPerAdministrationInKW;
    private BigDecimal electricityPricePerAdministration;
    private BigDecimal gasMeterReadingPerFlat;

    public MediaSummary() {
    }

    public MediaSummary(LocalDate createdAt, Flat flat, double electricityConsumptionInKW, double electricityAdministrationConsumptionInKW, double electricityMeterReadingPerFlat,double electricityMeterReadingPerAdministrationInKW, double gasConsumptionPerFlatInGJ, double gasConsumptionPerRealEstateInM3,double gasMeterReading, double waterConsumptionInM3,double electricityPricePerAdministration, double totalAdditionalCost,BigDecimal waterAdministrationCost) {
        this.flat = flat;
        this.createdAt = createdAt;
        this.electricityConsumptionInKW = new BigDecimal(electricityConsumptionInKW).setScale(2, RoundingMode.HALF_UP);
        this.electricityAdministrationConsumptionInKW = new BigDecimal(electricityAdministrationConsumptionInKW).setScale(2, RoundingMode.HALF_UP);
        this.electricityMeterReadingPerFlatInKW =new BigDecimal(electricityMeterReadingPerFlat).setScale(2, RoundingMode.HALF_UP);
        this.electricityMeterReadingPerAdministrationInKW = new BigDecimal(electricityMeterReadingPerAdministrationInKW).setScale(2, RoundingMode.HALF_UP);
        this.gasConsumptionPerFlatInGJ = new BigDecimal(gasConsumptionPerFlatInGJ).setScale(2, RoundingMode.HALF_UP);
        this.gasConsumptionPerRealEstateInM3 = new BigDecimal(gasConsumptionPerRealEstateInM3).setScale(2, RoundingMode.HALF_UP);
        this.gasMeterReadingPerFlat = new BigDecimal(gasMeterReading).setScale(2, RoundingMode.HALF_UP);
        this.waterConsumptionInM3 = new BigDecimal(waterConsumptionInM3).setScale(2, RoundingMode.HALF_UP);
        this.electricityPricePerAdministration = new BigDecimal(electricityPricePerAdministration).setScale(2, RoundingMode.HALF_UP);
        this.totalAdditionalCost = new BigDecimal(totalAdditionalCost).setScale(2, RoundingMode.HALF_UP);
        this.waterAdministrationCost = waterAdministrationCost;
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

    public BigDecimal getGasMeterReadingPerFlat() {
        return gasMeterReadingPerFlat;
    }
   public void addMediaSumarryAdditionalCosts(List<MediaSummaryAdditionalCost> mediaSummaryAdditionalCosts){
        mediaSummaryAdditionalCosts.stream().forEach(costs -> addMediaSummaryAdditionalCost(costs));
    }

    private void addMediaSummaryAdditionalCost(MediaSummaryAdditionalCost mediaSummaryAdditionalCost) {
        if(!this.mediaSummaryAdditionalCosts.contains(mediaSummaryAdditionalCost)){
            this.mediaSummaryAdditionalCosts.add(mediaSummaryAdditionalCost);
            mediaSummaryAdditionalCost.setMediaSummary(this);
        }
    }

}