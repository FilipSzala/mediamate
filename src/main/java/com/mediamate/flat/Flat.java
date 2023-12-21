package com.mediamate.flat;

import com.mediamate.meterValue.MeterValue;
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
    private String phoneNumber;
    @ElementCollection
    @CollectionTable (name = "flat_meter_value_map", joinColumns = @JoinColumn(name="flatId"))
    @MapKeyColumn (name = "day")
    @Column (name = "meter_value_id")
    private Map <LocalDate, MeterValue> meterValues = new HashMap<>();

}
