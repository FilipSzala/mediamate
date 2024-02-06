package com.mediamate.image;

import com.mediamate.meter.Meter;
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
    private Long realEstateId;
    @ManyToOne
    @JoinColumn(name = "meterId", referencedColumnName = "id")
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
