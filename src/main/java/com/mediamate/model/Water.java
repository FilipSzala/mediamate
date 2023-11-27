package com.mediamate.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
public class Water {
    private Long waterId;
    private double coldWater;
    private double hotWater;
}
