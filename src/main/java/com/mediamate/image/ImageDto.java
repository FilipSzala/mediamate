package com.mediamate.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Blob;
import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class ImageDto {
    private long id;
    private Blob image;
    private ImageType imageType;
    private LocalDate createDay;
}
