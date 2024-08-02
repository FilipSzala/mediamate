package com.mediamate.model.image.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
@JsonSerialize
@Getter
@Setter
public class ImageUrlResponse {
    private Long id;
    private String name;
    private String imageUrl;

    public ImageUrlResponse() {
    }

    public ImageUrlResponse(Long id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
