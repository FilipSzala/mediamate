package com.mediamate.model.cost.meter;

import com.mediamate.model.cost.flat.Flat;
import com.mediamate.model.cost.image.Image;
import com.mediamate.model.cost.realestate.RealEstate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Meter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double value;
    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(
            name = "image_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey (
                    name = "meter_iamge_fk"
                    )
    )
    private Image image;
    @ManyToOne
    @JoinColumn(
            name = "flatId",
            referencedColumnName = "id",
            foreignKey = @ForeignKey (
                    name = "meter_flat_fk"
            ))
    private Flat flat;
    @ManyToOne
    @JoinColumn (
            name = "real_estate_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey (
                    name = "meter_real_estate_fk"
            )
    )
    private RealEstate realEstate;
    @Enumerated
    private MeterType meterType;
    @Enumerated
    private MeterOwnership meterOwnership;
    private LocalDate createdAt;

    public Meter() {
    }
    public Meter(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Meter(Double value, MeterType meterType, LocalDate createdAt) {
        this.value = value;
        this.meterType = meterType;
        this.createdAt = createdAt;
    }


}
