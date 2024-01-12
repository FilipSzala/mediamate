package com.mediamate.realestate;

import com.mediamate.flat.Flat;
import com.mediamate.cost.Cost;
import com.mediamate.image.Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ownerId;
    private String address;
    @OneToMany(mappedBy = "realEstate")
    List<Flat> flats;
    @OneToMany
    @JoinColumn (name = "realEstateId")
    List<Cost> costs;
    @OneToMany
    @JoinColumn (name = "realEstateId")
    List<Image> images;
}
