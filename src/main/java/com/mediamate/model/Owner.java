package com.mediamate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OwnerId;
    private String name;
    @OneToMany
    @JoinColumn(name="realEstateId")
    private Set<RealEstate> realEstates;
    private String password;
    private LocalDate createDay = LocalDate.now();
}
