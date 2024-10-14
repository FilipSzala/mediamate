package com.mediamate.controller.photo;
import com.mediamate.model.image.ImageType;
import com.mediamate.model.image.response.ImageUrlResponse;
import com.mediamate.util.YearMonthDate;
import com.mediamate.model.image.Image;
import com.mediamate.model.image.request.ImageRequest;
import com.mediamate.model.image.ImageService;
import com.mediamate.controller.settlement.SettlementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/photos")


public class PhotoController {
    private ImageService imageService;
    private SettlementService settlementService;
    @Autowired
    public PhotoController(ImageService imageService, SettlementService settlementService) {
        this.imageService = imageService;
        this.settlementService = settlementService;
    }


    @GetMapping ("/distinct-date")
        public List<YearMonthDate> getDistinctDateFromImageByImageType(HttpSession httpSession,@RequestParam ("imageType") ImageType imageType){
        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        List<YearMonthDate> dates = imageService.getAllDistinctYearMonthDateByImageType(realEstateId,imageType);
            return dates;
        }

        @PostMapping("/by-type-and-date")
        public ResponseEntity<List<ImageUrlResponse>> getImagesByTypeAndDate(HttpSession httpSession, @RequestBody ImageRequest imageRequest){
            Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
            List <ImageUrlResponse> imagesUrl = imageService.convertImageToImageUrlResponse(imageRequest,realEstateId);
            return ResponseEntity.ok(imagesUrl);
        }


    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) throws SQLException, IOException {
        Image image = imageService.getImageById(id).get();
        byte[] imageBytes = image.getImage().getBytes(1, (int) image.getImage().length());

        MediaType mediaType;
        if (image.getImageType().equals(ImageType.METER)) {
            mediaType = MediaType.IMAGE_JPEG;
        } else {
            mediaType = MediaType.APPLICATION_PDF;
        }

        return ResponseEntity
                .ok()
                .contentType(mediaType)
                .body(imageBytes);
    }
}