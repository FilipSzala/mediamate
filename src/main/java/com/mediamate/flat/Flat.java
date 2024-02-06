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
    @JoinColumn(name = "realEstateId", referencedColumnName = "id")
    private RealEstate realEstate;
    //TODO: Create class for renters.
    @OneToMany(mappedBy = "flat")
    private List<Meter> meters = new ArrayList<>();

    public void addMeterToMeters(Meter meter){
        meters.add(meter);
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
