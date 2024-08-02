package com.mediamate.model.image;

import com.mediamate.model.meter.Meter;
import com.mediamate.model.real_estate.RealEstate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn (
            name = "real_estate_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey (
                    name = "image_real_estate_fk"
            )
    )
    private RealEstate realEstate;
    @OneToOne (mappedBy = "image")
    private Meter meter;
    @Lob
    private Blob image;
    private ImageType imageType;
    private LocalDate createAt = LocalDate.now();

    public void setBlob(MultipartFile file) throws IOException, SQLException {
        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
        image = blob;
    }
}
