package com.mediamate.user.role.owner;

import com.mediamate.realestate.RealEstate;
import com.mediamate.user.role.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("OWNER")
@Getter
@Setter
public class Owner extends UserRole {
    @OneToMany
    @JoinColumn(name="ownerId")
    private List<RealEstate> realEstates;
    public Owner(String firstName, String lastName) {
        super(firstName, lastName);
    }
    public Owner() {
    }
}
