package com.mediamate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Entity
@AllArgsConstructor
public class Flat {
    private Long flatId;
    private String renters;
    @OneToMany
    @JoinColumn(name="meterValueId")
    private Map <LocalDate,MeterValue> meterValues;

}
