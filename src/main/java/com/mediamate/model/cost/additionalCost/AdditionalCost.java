package com.mediamate.model.cost.additionalCost;

import com.mediamate.model.cost.Cost;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Month;

@Entity
@Getter
@Setter
public class AdditionalCost extends Cost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String information;
    private Double price;
    private Integer timePeriod;
    @Enumerated
    private ChargeType chargeType;
    public AdditionalCost() {
    }
    public AdditionalCost(String name, String information, Double price, Integer timePeriod, ChargeType chargeType) {
        this.name = name;
        this.information = information;
        this.price = price;
        this.timePeriod = timePeriod;
        this.chargeType = chargeType;
    }
}
