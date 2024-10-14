package com.mediamate.model.real_estate;

import com.mediamate.controller.profile_info.request.InitialRequest;
import com.mediamate.model.cost.Cost;
import com.mediamate.model.flat.Flat;
import com.mediamate.model.meter.Meter;
import com.mediamate.model.user.User;
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
            fetch = FetchType.LAZY,
            mappedBy = "realEstate",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST}
    )
    private List<Meter> meters= new ArrayList<>();
    @ManyToMany(
            mappedBy = "realEstates"
    )

    private List<User> users = new ArrayList<>();

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
    public  void addCosts (List <Cost> costs ){
        costs.stream().forEach(cost -> addCost(cost));
    }
    public void addMeter(Meter meter) {
        if (!this.meters.contains(meter)) {
            this.meters.add(meter);
            meter.setRealEstate(this);
        }
    }

    public void addUser(User user) {
        if (!this.users.contains(user)) {
            this.users.add(user);
            user.getRealEstates().add(this);
        }
    }


}