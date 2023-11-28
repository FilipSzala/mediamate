package com.mediamate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flatId;
    private String renters;
    @OneToMany
    @JoinColumn(name="meterValueId")
    private Map <LocalDate,MeterValue> meterValues;

}
