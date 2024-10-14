package com.mediamate.model.media_summary.additional_cost;

import com.mediamate.model.media_summary.MediaSummary;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Getter
@Setter
@NoArgsConstructor


public class MediaSummaryAdditionalCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (
            name = "media_summary_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey (name = "additional_cost_media_summary_fk")
    )
    private MediaSummary mediaSummary;
    private String name;
    private BigDecimal value;

    public MediaSummaryAdditionalCost(Long id, String name, double value) {
        this.id = id;
        this.name = name;
        this.value = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
    }

    public MediaSummaryAdditionalCost(BigDecimal value) {
        this.value = value;
    }
}
