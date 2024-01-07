package com.mediamate.cost;

import com.mediamate.cost.additionalCost.AdditionalCost;
import com.mediamate.cost.mediaCost.mediaCost;
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="mediaPriceId", referencedColumnName = "id")
    private mediaCost mediaCostPrice;
    @OneToMany
    @JoinColumn(name = "priceId")
    private List<AdditionalCost> additionalsCost;
    private LocalDate createdDay=LocalDate.now();
    public Cost(mediaCost mediaCostPrice) {
        this.mediaCostPrice = mediaCostPrice;
    }
}
