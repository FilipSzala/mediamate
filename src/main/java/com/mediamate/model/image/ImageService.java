package com.mediamate.model.image;


import com.mediamate.model.image.request.ImageRequest;
import com.mediamate.model.image.response.ImageUrlResponse;
import com.mediamate.model.real_estate.RealEstate;
import com.mediamate.model.real_estate.RealEstateRepository;
import com.mediamate.model.real_estate.RealEstateService;
import com.mediamate.util.PdfConverter;
import com.mediamate.util.YearMonthDate;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    RealEstateRepository realEstateRepository;
    @Autowired
    RealEstateService realEstateService;
    @Value("${APP_BACKEND_BASE_URL}")
    private String appBaseUrl;

    public Image createImage (MultipartFile file,String expiryDate, ImageType imageType, HttpSession httpSession) throws SQLException, IOException {
        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate =realEstateService.findById(realEstateId).orElseThrow();
        Image image = new Image();
        String contentType = file.getContentType();
        boolean isInspectionOrInvoiceType=imageType.equals(ImageType.INVOICE)||imageType.equals(ImageType.INSPECTION);
        if(isInspectionOrInvoiceType) {
            image.setName(file.getOriginalFilename());
        }
        boolean isExpiryDateNotNullAndNotEmpty = (expiryDate!=null&&!expiryDate.isEmpty());
        if(isExpiryDateNotNullAndNotEmpty){
            image.setExpiryDate(LocalDate.parse(expiryDate));
        }
        boolean shouldConvertToPdf = imageType.equals(ImageType.INSPECTION)&&!contentType.equals("application/pdf");
        byte[] fileBytes = shouldConvertToPdf
                ? PdfConverter.convertImageToPdf(file.getBytes())
                : file.getBytes();

        image.setBlob(fileBytes);
        image.setImageType(imageType);
        image.setRealEstate(realEstate);
        imageRepository.save(image);
        return image;
    }

    public List<Image> createImages (List<MultipartFile> files, List<String> expiryDates, ImageType imageType,HttpSession httpSession){
        if (!expiryDates.isEmpty()&&files.size() != expiryDates.size()) {
            throw new IllegalArgumentException("Mismatch between files and expiry dates");
        }
        return IntStream.range(0, files.size())
                .mapToObj(i -> {
                    try {
                        String expiryDate = expiryDates.isEmpty() ? "" : expiryDates.get(i);
                        return createImage(files.get(i), expiryDate, imageType, httpSession);
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
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

    public List<ImageUrlResponse> convertImageToImageUrlResponse(ImageRequest imageRequest, Long realEstateId) {
        List<Image> images;
        images = imageRequest.isEmpty()? getRealEstateInspectionImagesWithExpiry(realEstateId):getImagesByTypeAndDate(realEstateId, imageRequest);
        List<ImageUrlResponse> imagesUrl =
                images.stream()
                        .map(image -> {
                            ImageUrlResponse imageResponse = new ImageUrlResponse();
                            imageResponse.setName(image.getName());
                            imageResponse.setImageUrl(generateImageUrl(image.getId()));
                            return imageResponse;
                        })
                        .collect(Collectors.toList());
        return imagesUrl;
    }

    private List<Image> getRealEstateInspectionImagesWithExpiry(Long realEstateId) {
        LocalDate currentDay = LocalDate.now();
       return imageRepository.findByRealEstateIdInspectionImagesWithExpiry(realEstateId,currentDay);
    }

    private String generateImageUrl(Long imageId) {
        return this.appBaseUrl  + "/photos/" + imageId;
    }
}
