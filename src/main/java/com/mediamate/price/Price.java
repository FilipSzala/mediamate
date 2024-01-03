package com.mediamate.price;

import com.mediamate.price.additionalCost.AdditionalCost;
import com.mediamate.price.media.Media;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.YearMonth;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name ="mediaPriceId", referencedColumnName = "id")
    private Media mediaPrice;
    @OneToMany
    @JoinColumn(name = "priceId")
    private List<AdditionalCost> additionalsCost;
    private YearMonth createdMonth=YearMonth.now();

    public Price(Media mediaPrice) {
        this.mediaPrice = mediaPrice;
    }
}
