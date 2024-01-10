package com.mediamate.flat;

import com.mediamate.meter.Meter;
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
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long realEstateId;
    private String renters;
    private String phoneNumber;
    @OneToMany(mappedBy = "flat")
    private List<Meter> meters = new ArrayList<>();

    public void addMeterToMetersList (Meter meter){
        meters.add(meter);
    }
}
