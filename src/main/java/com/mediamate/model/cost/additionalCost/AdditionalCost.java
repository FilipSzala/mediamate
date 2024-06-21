package com.mediamate.model.additionalCost;

import com.mediamate.model.Cost;
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
    private Month timePeriod;
    @Enumerated
    private ChargeType chargeType;
    public AdditionalCost() {
    }
    public AdditionalCost(String name, String information, Double price, Month timePeriod, ChargeType chargeType) {
        this.name = name;
        this.information = information;
        this.price = price;
        this.timePeriod = timePeriod;
        this.chargeType = chargeType;
    }
}
