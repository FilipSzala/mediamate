package com.mediamate.realestate;

import com.mediamate.cost.Cost;
import com.mediamate.flat.Flat;
import com.mediamate.initialSetup.request.InitialRequest;
import com.mediamate.meter.Meter;

import com.mediamate.user.User;
import com.mediamate.user.role.owner.Owner;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class RealEstate implements Serializable {
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
    @OneToMany(
            mappedBy = "realEstate",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST}
    )
    private List<Meter> meters= new ArrayList<>();
    @ManyToOne
    @JoinColumn(
            name = "userId",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "realestate_user_fk"
            ))
    private User user;

    public RealEstate() {
    }

    public RealEstate(String address) {
        this.address = address;
    }


    public void addFlats (List<InitialRequest.Flat> requestFlats){
        this.flats = requestFlats.stream()
                .map(requestFlat -> {
                    Flat flat = new Flat();
                    flat.setRenter(requestFlat.getRenterRequest());
                    flat.setRealEstate(this);
                    return flat;
                }).collect(Collectors.toList());
        }
    public void addCost(Cost cost) {
        if (!this.costs.contains(cost)) {
            this.costs.add(cost);
            cost.setRealEstate(this);

        }
    }

    public void addMeter(Meter meter) {
        if (!this.meters.contains(meter)) {
            this.meters.add(meter);
            meter.setRealEstate(this);
        }
    }


}