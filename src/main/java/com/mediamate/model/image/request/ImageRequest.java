package com.mediamate.model.image.request;

import com.mediamate.model.image.ImageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageRequest {
    ImageType imageType;
    int createdYear;
    int createdMonth;
}
