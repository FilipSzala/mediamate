package com.mediamate.image;


import com.mediamate.YearMonthResult;
import com.mediamate.image.request.ImageRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")

public class ImageController {
    @Autowired
    private ImageService imageService;
    @GetMapping ("/types-and-dates")
        public Map<String,Object> getImageTypeAndDistinctDates (HttpSession httpSession){
            Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
            List<YearMonthResult> dates = imageService.getAllDistinctYearMonthDate(realEstateId);
            Map imageTypeAndDistincDates =new HashMap<String,Object>();
            imageTypeAndDistincDates.put("imageType",ImageType.values());
            imageTypeAndDistincDates.put("distinctDates",dates);
            return imageTypeAndDistincDates;
        }

        @GetMapping("/filter-by-date-and-type")
        public List<Image> getImagesByTypeAndDate(HttpSession httpSession, @RequestBody ImageRequest imageRequest){
            Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
            List<Image> images = imageService.getImagesByRealEstateIdAndTypeAndDate(realEstateId,imageRequest);
            return images;
        }


    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) throws SQLException, IOException {
        Image image = imageService.getImageById(id).get();
        byte[] imageBytes = image.getImage().getBytes(1, (int) image.getImage().length());
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageBytes);
    }


    //TODO: Remember to change default value for maximum picture size. (current 2 MB)
    @PostMapping()
    public ResponseEntity<?> createImage(@RequestParam("image") MultipartFile file, HttpSession httpSession) throws IOException, SQLException {
        imageService.createImage(file,null, httpSession);
        return ResponseEntity
                .ok()
                .body("Image added");
}}