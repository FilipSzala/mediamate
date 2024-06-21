package com.mediamate.user.role.owner;

import com.mediamate.model.cost.realestate.RealEstate;
import com.mediamate.user.role.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("OWNER")
@Getter
@Setter
public class Owner extends UserRole {
    @OneToMany (
            mappedBy = "owner",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<RealEstate> realEstates = new ArrayList<>();
    public Owner(String firstName, String lastName) {
        super(firstName, lastName);
    }
    public Owner() {
    }

    public void addRealEstates (List<RealEstate> listRealEstates){
        for (RealEstate realEstate : listRealEstates){
            if(!this.realEstates.contains(realEstate)){
                this.realEstates.add(realEstate);
                realEstate.setOwner(this);
            }
        }
    }
}
