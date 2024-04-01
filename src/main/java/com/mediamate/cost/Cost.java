package com.mediamate.cost;

import com.mediamate.cost.additionalCost.AdditionalCost;
import com.mediamate.cost.mediaCost.MediaCost;
import com.mediamate.realestate.RealEstate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
