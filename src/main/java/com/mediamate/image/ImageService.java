package com.mediamate.image;

import com.mediamate.realestate.RealEstate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;
    public void createImage (MultipartFile file) throws SQLException, IOException {
        Image image = new Image();
        image.setBlob(file);
        imageRepository.save(image);
    }
    public void createImages (List<MultipartFile> files){
        files.stream()
                .forEach(file -> {
                    try {
                        createImage(file);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public void updateImagePartially(Long imageId, Image modifiedImage) {
        Image databaseImage = getImageById(imageId).orElseThrow();
        databaseImage.setMeterId(modifiedImage.getMeterId());
        databaseImage.setImage(modifiedImage.getImage());
        databaseImage.setCreateDay(modifiedImage.getCreateDay());
        imageRepository.save(databaseImage);

    }

    public List<Image> getImages (){
        return imageRepository.findAll();
    }

    public Optional <Image> getImageById(Long imageId){
        return imageRepository.findById(imageId);
    }

    public void delete(Long imageId) {
        Image image = getImageById(imageId).get();
        imageRepository.delete(image);
    }
}
