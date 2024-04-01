package com.mediamate.cost.mediaCost;

import com.mediamate.cost.Cost;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MediaCost extends Cost {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private double gas;
    private double electricity;
    private double water;

    public MediaCost() {
    }

    public MediaCost(double gas, double electricity, double water) {
        this.gas = gas;
        this.electricity = electricity;
        this.water = water;
    }
}
