package com.mediamate.owner;

import com.mediamate.realestate.RealEstate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ownerId;
    private String firstName;
    private String lastName;
    @OneToMany (fetch = FetchType.EAGER)
    @JoinColumn(name="ownerId")
    private List<RealEstate> realEstates;

    private LocalDate createDay = LocalDate.now();

    public Owner(String firstName, String lastName, List<RealEstate> realEstates) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.realEstates = realEstates;
    }
}
