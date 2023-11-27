package com.mediamate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
public class MeterValue {
    private Long meterValueId;
    private double electricity;
    private double gas;
    @OneToOne
    @JoinColumn(name ="waterId")
    Water water;
}
