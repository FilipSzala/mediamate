package com.mediamate.image;


import com.mediamate.YearMonthResult;
import com.mediamate.image.request.ImageRequest;
import com.mediamate.settlement.SettlementService;
import com.mediamate.settlement.request.MeterRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/images")

public class ImageController {
    private ImageService imageService;
    SettlementService settlementService;
    @Autowired
    public ImageController(ImageService imageService, SettlementService settlementService) {
        this.imageService = imageService;
        this.settlementService = settlementService;
    }

    @GetMapping ("/types-and-dates")
        public Map<String,Object> getImageTypeAndDistinctDates (HttpSession httpSession){
            Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
            List<YearMonthResult> dates = imageService.getAllDistinctYearMonthDate(realEstateId);
            Map imageTypeAndDistincDates =new HashMap<String,Object>();
            imageTypeAndDistincDates.put("imageType",ImageType.values());
            imageTypeAndDistincDates.put("distinctDates",dates);
            return imageTypeAndDistincDates;
        }

        @GetMapping("/by-date-and-type")
        public List<Image> getImagesByTypeAndDate(HttpSession httpSession, @RequestBody ImageRequest imageRequest){
            Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
            List<Image> images = imageService.getImagesByTypeAndDate(realEstateId,imageRequest);
            return images;
        }
        @PostMapping()
        public ResponseEntity<?> createImages(@RequestParam("images") List<MultipartFile> files,HttpSession httpSession){
            imageService.createImages(files,null,httpSession);
            return ResponseEntity
                    .ok()
                    .body("Images added");
        }
        /* @GetMapping()
        public List<ImageDto> getImagesWithoutType (HttpSession httpSession){
        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        List<Image> images = imageService.getImagesWithoutTypeInCurrentDay(realEstateId);
        List <ImageDto> imageDtos = ImageMapper.mapToImageDtos(images);
        return imageDtos;
        }*/

        @PostMapping("/meter")
        public ResponseEntity<String> setupMeterWithoutConfirm(@RequestBody MeterRequest meterRequest,HttpSession httpSession) {
        String response = settlementService.redirectForSetupMeter(meterRequest,false,httpSession);
        return ResponseEntity.ok(response);
        }
         @PostMapping("/meter-with-confirm")
        public ResponseEntity<String> setupMeterWithConfirm(HttpSession httpSession) {
        String response = settlementService.redirectForSetupMeter(new MeterRequest(),true,httpSession);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) throws SQLException, IOException {
        Image image = imageService.getImageById(id).get();
        Blob blob = image.getImage();
        blob.length();
        long test = image.getImage().length();
        int test2 = (int) test;

        byte[] imageBytes = image.getImage().getBytes(1, (int) image.getImage().length());
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageBytes);
    }
}