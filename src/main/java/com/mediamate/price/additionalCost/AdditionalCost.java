package com.mediamate.price.additionalCost;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
public class AdditionalCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long priceId;
    private String name;
    private Double cost;
    private LocalDate timePeriod;
    private ChargeType chargeType;

}
