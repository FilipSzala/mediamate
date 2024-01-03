package com.mediamate.settlement;

import com.mediamate.flat.Flat;
import com.mediamate.flat.FlatService;
import com.mediamate.image.Image;
import com.mediamate.image.ImageService;
import com.mediamate.price.media.Media;
import com.mediamate.price.media.MediaService;
import com.mediamate.settlement.request.MeterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("settlement")
public class SettlementController {
    ImageService imageService;
    SettlementService settlementService;
    FlatService flatService;
    MediaService mediaService;
    @Autowired
    public SettlementController(ImageService imageService, SettlementService settlementService, FlatService flatService,MediaService mediaService) {
        this.imageService = imageService;
        this.settlementService = settlementService;
        this.flatService = flatService;
        this.mediaService = mediaService;
    }

    @GetMapping("/images")
    public List<Image> getAllImages (){
        return imageService.getImages();
    }

    @PostMapping("/images")

    public ResponseEntity<?> createImages(@RequestParam("images") List<MultipartFile> files){
        imageService.createImages(files);
        return ResponseEntity
                .ok()
                .body("Images added");
    }

    @DeleteMapping("/image/{imageId}")
    public void deleteImage(@PathVariable Long imageId){
        imageService.delete(imageId);
    }

    @GetMapping("/flats")
    public List<Flat> getFlatsByRealEstateId (@RequestParam Long realEstateId){
        return flatService.findFlatsByRealEstateId(realEstateId);
    }

    @PostMapping ("/meter")
    public void setupMeter (@RequestBody MeterRequest meterRequest){
        settlementService.setupMeter(meterRequest);
    }

    @PostMapping("/price/media")
    public void createMediaPrice (@RequestBody Media media){
        mediaService.createMedia(media);
    }

}
