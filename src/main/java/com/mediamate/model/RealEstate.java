package com.mediamate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ownerId;
    private String address;
    @OneToMany
    @JoinColumn(name="flatId")
    Set<Flat> flats;
}
