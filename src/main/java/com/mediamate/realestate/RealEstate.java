package com.mediamate.realestate;

import com.mediamate.flat.Flat;
import com.mediamate.cost.Cost;
import com.mediamate.image.Image;
import com.mediamate.meter.Meter;
import com.mediamate.user.role.owner.Owner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne ()
    @JoinColumn(
            name = "owner_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "real_estate_owner_fk"
            )
    )
    private Owner owner;
    private String address;
    @OneToMany(
            mappedBy = "realEstate",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<Flat> flats= new ArrayList<>();
    @OneToMany (
            mappedBy = "realEstate",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST}
    )
    private List<Cost> costs= new ArrayList<>();
    @OneToMany (
            mappedBy = "realEstate",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST}
    )
    private List<Image> images= new ArrayList<>();
    @OneToMany(
            mappedBy = "realEstate",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST}
    )
    private List<Meter> meters= new ArrayList<>();

    public RealEstate() {
    }

    public RealEstate(String address) {
        this.address = address;
    }


    public void addFlats (List<Flat> listFlat){
        for (Flat flat : listFlat) {
            if (!this.flats.contains(flat)) {
                this.flats.add(flat);
                flat.setRealEstate(this);
            }
        }
    }
    public void addCost(Cost cost) {
        if (!this.costs.contains(cost)) {
            this.costs.add(cost);
            cost.setRealEstate(this);

        }
    }
    public void addImage (Image image){
        if (!this.images.contains(image)){
            this.images.add(image);
            image.setRealEstate(this);
        }
    }
    public void addMeter(Meter meter) {
        if (!this.meters.contains(meter)) {
            this.meters.add(meter);
            meter.setRealEstate(this);
        }
    }


}