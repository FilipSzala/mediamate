package com.mediamate.cost.additionalCost;

import com.mediamate.cost.Cost;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Month;

@Entity
@Getter
@Setter
public class AdditionalCost extends Cost{
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
