package com.mediamate.model.image.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
@JsonSerialize
@Getter
@Setter
public class ImageUrlResponse {
    private String name;
    private String imageUrl;

    public ImageUrlResponse() {
    }

    public ImageUrlResponse(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
