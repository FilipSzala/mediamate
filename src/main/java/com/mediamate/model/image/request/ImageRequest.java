package com.mediamate.model.cost.image.request;

import com.mediamate.model.cost.image.ImageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ImageRequest {
    ImageType imageType;
    int createdYear;
    int createdMonth;
}
