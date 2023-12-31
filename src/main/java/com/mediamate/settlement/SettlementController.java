package com.mediamate.settlement;

import com.mediamate.cost.additionalCost.AdditionalCost;
import com.mediamate.cost.additionalCost.AdditionalCostService;
import com.mediamate.cost.mediaCost.MediaCostService;
import com.mediamate.cost.mediaCost.mediaCost;
import com.mediamate.flat.Flat;
import com.mediamate.flat.FlatService;
import com.mediamate.image.Image;
import com.mediamate.image.ImageService;
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
    MediaCostService mediaCostService;
    AdditionalCostService additionalCostService;
    @Autowired
    public SettlementController(ImageService imageService, SettlementService settlementService, FlatService flatService, MediaCostService mediaCostService, AdditionalCostService additionalCostService) {
        this.imageService = imageService;
        this.settlementService = settlementService;
        this.flatService = flatService;
        this.mediaCostService = mediaCostService;
        this.additionalCostService = additionalCostService;
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

    @PostMapping("/media-cost")
    public void createMediaCost (@RequestBody mediaCost mediaCost){
        mediaCostService.createMedia(mediaCost);
    }
    @PostMapping("/additional-cost")
        public void createAdditionalCost(@RequestBody AdditionalCost additionalCost){
        additionalCostService.createAdditionalCost(additionalCost);
    }
}
