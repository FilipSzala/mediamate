package com.mediamate.image;

import java.util.List;
import java.util.stream.Collectors;

public class ImageMapper {

    private ImageMapper() {
    }

    public static List<ImageDto> mapToImageDtos (List <Image> images){
        return
                images.stream()
                        .map(image -> mapToImageDto(image))
                        .collect(Collectors.toList());
    }

    private static ImageDto mapToImageDto(Image image) {
        return
                ImageDto.builder()
                        .id(image.getId())
                        .image(image.getImage())
                        .imageType(image.getImageType())
                        .createDay(image.getCreateAt())
                        .build()
                ;
    }
}
