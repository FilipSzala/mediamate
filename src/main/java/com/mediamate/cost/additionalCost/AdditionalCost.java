package com.mediamate.cost.additionalCost;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Month;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdditionalCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long priceId;
    private String name;
    private String information;
    private Double cost;
    private Month timePeriod;
    private ChargeType chargeType;

}
