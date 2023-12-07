package com.mediamate.metervalue;

import com.mediamate.metervalue.water.Water;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeterValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double electricity;
    private double gas;
    @OneToOne
    @JoinColumn(name ="waterId", referencedColumnName = "id")
    Water water;
}
