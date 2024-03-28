package com.mediamate.cost.additionalCost;

import com.mediamate.cost.Cost;
import jakarta.persistence.*;
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
    private String name;
    private String information;
    private Double price;
    private Month timePeriod;
    private ChargeType chargeType;
    @ManyToOne
    @JoinColumn (
            name = "cost_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey (
                    name = "additional_cost_fk"
            )
    )
    private Cost cost;

}
