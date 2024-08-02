package com.mediamate.model.image;


import com.mediamate.config.security.SecurityService;
import com.mediamate.model.image.request.ImageRequest;
import com.mediamate.model.image.response.ImageUrlResponse;
import com.mediamate.model.real_estate.RealEstate;
import com.mediamate.model.real_estate.RealEstateRepository;
import com.mediamate.model.real_estate.RealEstateService;
import com.mediamate.util.YearMonthDate;
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

    public Image createImage (MultipartFile file, ImageType imageType, HttpSession httpSession) throws SQLException, IOException {
        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate =realEstateService.findById(realEstateId).orElseThrow();
        Image image = new Image();
        image.setBlob(file);
        image.setImageType(imageType);
        image.setRealEstate(realEstate);
        imageRepository.save(image);
        return image;
    }

    public List<Image> createImages (List<MultipartFile> files, ImageType imageType,HttpSession httpSession){
        return files.stream()
                .map(file -> {
                    try {
                        return createImage(file, imageType, httpSession);
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
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
    public List<YearMonthDate> getAllDistinctYearMonthDateByImageType(Long realEstateId, ImageType imageType){
        List<YearMonthDate> dates = imageRepository.findAllDistinctYearMonthByRealEstateId(realEstateId,imageType);
        return dates;
    }


    public List<ImageUrlResponse> findImagesByImageRequest(ImageRequest imageRequest, Long realEstateId) {
        List<Image> images = getImagesByTypeAndDate(realEstateId,imageRequest);
        List<ImageUrlResponse> imagesUrl =
                images.stream()
                .map(this::convertToImageUrlResponse)
                .collect(Collectors.toList());
        return imagesUrl;
    }
    private ImageUrlResponse convertToImageUrlResponse(Image image) {
        String imageUrl = generateImageUrl(image.getId());
        return new ImageUrlResponse(image.getId(),image.getImageType().toString(), imageUrl);
    }
    private String generateImageUrl(Long imageId) {
        return "http://localhost:8080/photos/" + imageId;
    }
}
