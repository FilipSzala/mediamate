package com.mediamate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
public class Owner {
    private Long OwnerId;
    private String name;
    @OneToMany
    @JoinColumn(name="realEstateId")
    private Set<RealEstate> realEstates;
    private String password;
    private LocalDate createDay = LocalDate.now();
}
