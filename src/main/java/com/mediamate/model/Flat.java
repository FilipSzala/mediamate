package com.mediamate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
    @ElementCollection
    @CollectionTable (name = "flat_meter_value_mapping", joinColumns = @JoinColumn(name="flat_id"))
    @MapKeyColumn (name = "data_pomiaru")
    @Column (name = "meter_value_id")
    private Map <LocalDate,MeterValue> meterValues = new HashMap<>();

}
