package com.mediamate.meter;

import com.mediamate.image.Image;
import com.mediamate.meter.water.Water;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @OneToMany
    @JoinColumn(name = "materId")
    private List<Image> images;

    public void setImages(Image image) {
        images.add(image);
    }
}
