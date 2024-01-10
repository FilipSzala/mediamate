package com.mediamate.image;

import com.mediamate.meter.MeterType;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateDto;
import com.mediamate.realestate.RealEstateRepository;
import com.mediamate.security.SecurityService;
import jakarta.servlet.http.HttpSession;
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
    @Autowired
    RealEstateRepository realEstateRepository;

    @Autowired
    SecurityService securityService;
    public void createImage (MultipartFile file,ImageType imageType) throws SQLException, IOException {
        Image image = new Image();
        image.setBlob(file);
        image.setImageType(imageType);
        imageRepository.save(image);
    }
    public void createImages (List<MultipartFile> files, ImageType imageType){
        files.stream()
                .forEach(file -> {
                    try {
                        createImage(file, imageType);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }









    public void updateImagePartially(Long imageId, Image modifiedImage) {
        Image databaseImage = getImageById(imageId).orElseThrow();
        databaseImage.setMeter(modifiedImage.getMeter());
        databaseImage.setImage(modifiedImage.getImage());
        databaseImage.setCreateDay(modifiedImage.getCreateDay());
        imageRepository.save(databaseImage);

    }

/*    public List<Image> getImages (){
        return realEstateRepository.findAllImagesByRealEstateId(2L);
    }*/
    public List<Image> getImagesLogInUserByType (){
        List<RealEstateDto> realEstateDtos = securityService.getRealEstatesDtoBySession();

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
