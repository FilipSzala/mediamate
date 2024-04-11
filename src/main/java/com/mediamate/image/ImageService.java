package com.mediamate.image;

import com.mediamate.YearMonthDate;
import com.mediamate.image.request.ImageRequest;
import com.mediamate.realestate.RealEstate;
import com.mediamate.realestate.RealEstateRepository;
import com.mediamate.realestate.RealEstateService;
import com.mediamate.security.SecurityService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    RealEstateRepository realEstateRepository;
    @Autowired
    SecurityService securityService;

    @Autowired
    RealEstateService realEstateService;

    public void createImage (MultipartFile file,ImageType imageType,HttpSession httpSession) throws SQLException, IOException {
        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate =realEstateService.findById(realEstateId).orElseThrow();


        Image image = new Image();
        image.setBlob(file);
        image.setImageType(imageType);
        image.setRealEstate(realEstate);
        imageRepository.save(image);
    }

    public void createImages (List<MultipartFile> files, ImageType imageType,HttpSession httpSession){
        files.stream()
                .forEach(file -> {
                    try {
                        createImage(file, imageType,httpSession);
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
        databaseImage.setCreateAt(modifiedImage.getCreateAt());
        imageRepository.save(databaseImage);
    }

  public List<Image> getImagesByImageTypeInCurrentDay(HttpSession httpSession, ImageType imageType){
        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        List<Image> images = imageRepository.findImagesByRealEstateIdAndImageTypeForCurrentDay(realEstateId,imageType,LocalDate.now());
        return images;
    }
    public  List<Image> getImagesByTypeAndDate(Long realEstateId, ImageRequest imageRequest){
        List<Image> images = imageRepository.findImagesByRealEstateIdAndImageTypeAndYearMonth(
                realEstateId,
                imageRequest.getImageType(),
                imageRequest.getCreatedYear(),
                imageRequest.getCreatedMonth());
        return images;
    }
    public Optional <Image> getImageById(Long imageId){
        return imageRepository.findById(imageId);
    }

    public void delete(Long imageId) {
        Image image = getImageById(imageId).get();
        imageRepository.delete(image);
    }
    public List<YearMonthDate> getAllDistinctYearMonthDate(Long realEstateId){
        List<YearMonthDate> dates = imageRepository.findAllDistinctYearMonthByRealEstateId(realEstateId);
        return dates;
    }


}
