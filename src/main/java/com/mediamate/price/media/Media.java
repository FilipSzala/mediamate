package com.mediamate.price.media;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Media {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private double gas;
    private double electricity;
    private double water;
}
