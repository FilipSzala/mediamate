package com.mediamate.model.cost;

import com.mediamate.model.real_estate.RealEstate;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn ( name = "COST")

public abstract class Cost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(
            name = "real_estate_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey (
                    name = "cost_real_estate_fk"
            )
    )
    private RealEstate realEstate;
    private LocalDate createdAt =LocalDate.now();

    public Cost() {
    }

    public void setRealEstate(RealEstate realEstate) {
        this.realEstate = realEstate;
    }
}
