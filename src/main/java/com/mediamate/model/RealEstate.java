package com.mediamate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;

import java.util.Set;

@Entity
@AllArgsConstructor
public class RealEstate {
    private Long realEstateId;
    private String address;
    @OneToMany
    @JoinColumn(name="flatId")
    Set<Flat> flats;
}
