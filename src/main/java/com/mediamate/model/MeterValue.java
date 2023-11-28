package com.mediamate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MeterValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meterValueId;
    private double electricity;
    private double gas;
    @OneToOne
    @JoinColumn(name ="waterId")
    Water water;
}
