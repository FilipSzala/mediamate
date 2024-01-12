package com.mediamate.image.request;

import com.mediamate.image.ImageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ImageRequest {
    ImageType imageType;
    int year;
    int month;
}
