package com.mediamate.cost;

import com.mediamate.cost.additionalCost.AdditionalCost;
import com.mediamate.cost.mediaCost.MediaCost;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Cost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long realEstateId;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn (
            name ="media_cost_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "cost_media_cost_fk"
            ))
    private MediaCost mediaCost;
    @OneToMany(mappedBy = "cost",
                cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<AdditionalCost> additionalCosts;
    private LocalDate createdAt =LocalDate.now();
    public Cost(MediaCost mediaCost) {
        this.mediaCost = mediaCost;
    }
}
