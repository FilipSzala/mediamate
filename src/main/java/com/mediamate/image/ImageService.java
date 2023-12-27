package com.mediamate.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;
    public void createImage (Image image){
        imageRepository.save(image);
    }

    public List<Image> getImages (){
        return imageRepository.findAll();
    }

    public Optional <Image> getImageById(Long imageId){
        return imageRepository.findById(imageId);
    }
}
