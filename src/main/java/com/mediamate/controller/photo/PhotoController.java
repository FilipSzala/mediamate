package com.mediamate.photo;


import com.mediamate.date.YearMonthDate;
import com.mediamate.image.Image;
import com.mediamate.image.ImageType;
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

public class PhotoController {
    private PhotoService photoService;
    SettlementService settlementService;
    @Autowired
    public PhotoController(PhotoService photoService, SettlementService settlementService) {
        this.photoService = photoService;
        this.settlementService = settlementService;
    }

    @GetMapping ("/type-and-date")
        public Map<String,Object> getTypeAndDistinctDateFromImage(HttpSession httpSession){
            Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
            List<YearMonthDate> dates = photoService.getAllDistinctYearMonthDate(realEstateId);
            Map imageTypeAndDistincDates =new HashMap<String,Object>();
            imageTypeAndDistincDates.put("imageType", ImageType.values());
            imageTypeAndDistincDates.put("distinctDates",dates);
            return imageTypeAndDistincDates;
        }

        @GetMapping("/by-type-and-date")
        public List<Image> getImagesByTypeAndDate(HttpSession httpSession, @RequestBody ImageRequest imageRequest){
            Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
            List<Image> images = photoService.getImagesByTypeAndDate(realEstateId,imageRequest);
            return images;
        }
        @PostMapping()
        public ResponseEntity<?> createImages(@RequestParam("images") List<MultipartFile> files,HttpSession httpSession){
            photoService.createImages(files,null,httpSession);
            return ResponseEntity
                    .ok()
                    .body("Images added");
        }

        /* @GetMapping()
        public List<ImageDto> getImagesWithoutType (HttpSession httpSession){
        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        List<Image> images = photoService.getImagesWithoutTypeInCurrentDay(realEstateId);
        List <ImageDto> imageDtos = ImageMapper.mapToImageDtos(images);
        return imageDtos;
        }*/

        @PostMapping("/meter")
        public ResponseEntity<String> setupMeterWithoutConfirm(@RequestBody MeterRequest meterRequest,HttpSession httpSession) {
            String response = settlementService.redirectForSetupMeter(meterRequest, false, httpSession);
        return ResponseEntity.ok(response);
        }
         @PostMapping("/meter-with-confirm")
        public ResponseEntity<String> setupMeterWithConfirm(HttpSession httpSession) {
        String response = settlementService.redirectForSetupMeter(new MeterRequest(),true,httpSession);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) throws SQLException, IOException {
        Image image = photoService.getImageById(id).get();
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