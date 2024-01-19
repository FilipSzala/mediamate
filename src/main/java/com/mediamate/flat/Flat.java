package com.mediamate.flat;

import com.mediamate.meter.Meter;
import com.mediamate.realestate.RealEstate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Flat  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "realEstateId", referencedColumnName = "id")
    private RealEstate realEstate;
    private String rentersFullName;
    private int renetersCount;
    private String phoneNumber;
    @OneToMany(mappedBy = "flat")
    private List<Meter> meters = new ArrayList<>();

    public void addMeterToMetersList (Meter meter){
        meters.add(meter);
    }
}
