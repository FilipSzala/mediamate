package com.mediamate.meter;

import com.mediamate.flat.Flat;
import com.mediamate.image.Image;
import com.mediamate.meter.water.Water;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Meter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double electricity;
    private double gas;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="waterId", referencedColumnName = "id")
    private Water water;
    @OneToMany(mappedBy = "meter")
    private List<Image> images;
    @ManyToOne
    @JoinColumn(name = "flatId", referencedColumnName = "id")
    private Flat flat;
    private LocalDate createdAt = LocalDate.now();

    public void setImages(Image image) {
        images.add(image);
    }
}
