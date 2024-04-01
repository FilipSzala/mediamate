package com.mediamate.flat;

import com.mediamate.meter.Meter;
import com.mediamate.realestate.RealEstate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Flat extends Renter  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(
            name = "real_estate_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey (
                    name = "flat_real_estate_id"
            ))
    private RealEstate realEstate;
    @OneToMany(
            mappedBy = "flat",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<Meter> meters = new ArrayList<>();

    public void addMeter(Meter meter) {
        if (!this.meters.contains(meter)) {
            this.meters.add(meter);
            meter.setFlat(this);
        }
    }
    public Flat(){
    }
    public Flat(Long id, RealEstate realEstate, List<Meter> meters, String rentersFullName, int renterCount, String phoneNumber) {
        super(rentersFullName, renterCount, phoneNumber);
        this.id = id;
        this.realEstate = realEstate;
        this.meters = meters;
    }
}
