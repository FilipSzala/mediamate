package com.mediamate.cost.mediaCost;

import com.mediamate.cost.Cost;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MediaCost {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private double gas;
    private double electricity;
    private double water;
    @OneToOne(mappedBy = "mediaCost")
    private Cost cost;
}
