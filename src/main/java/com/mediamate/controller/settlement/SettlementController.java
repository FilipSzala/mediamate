package com.mediamate.settlement;

import com.mediamate.cost.additionalCost.AdditionalCost;
import com.mediamate.cost.additionalCost.AdditionalCostService;
import com.mediamate.cost.mediaCost.MediaCostService;
import com.mediamate.cost.mediaCost.MediaCost;
import com.mediamate.flat.Flat;
import com.mediamate.flat.FlatService;
import com.mediamate.image.*;
import com.mediamate.photo.PhotoInformationRequest;
import com.mediamate.photo.PhotoService;
import com.mediamate.settlement.request.MeterRequest;
import com.mediamate.summary.SummaryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("settlement")
public class SettlementController {
    PhotoService photoService;
    SettlementService settlementService;
    FlatService flatService;
    MediaCostService mediaCostService;
    AdditionalCostService additionalCostService;
    SummaryService summaryService;



    @Autowired
    public SettlementController(PhotoService photoService, SettlementService settlementService, FlatService flatService, MediaCostService mediaCostService, AdditionalCostService additionalCostService, SummaryService summaryService) {
        this.photoService = photoService;
        this.settlementService = settlementService;
        this.flatService = flatService;
        this.mediaCostService = mediaCostService;
        this.additionalCostService = additionalCostService;
        this.summaryService = summaryService;

    }

    //Methods in this class depend on Real Estate's session.Therefore, if I coded method named "getFlats" it means that this method returns
    //all Flats by realEstateId from session.
    @PostMapping("/images")
    public ResponseEntity<?> createImagesWithMeterType(@RequestParam("images") List<MultipartFile> files,HttpSession httpSession){
        photoService.createImages(files, ImageType.METER,httpSession);
        return ResponseEntity
                .ok()
                .body("Images added");
    }
    //It should be fill out by form-data.
    @PostMapping ("/images2")
    public ResponseEntity<?> createMeterWithPhotoAndInformation(@RequestParam ("files") List<MultipartFile> files, @RequestPart("infoRequest") List <PhotoInformationRequest> infoRequest, HttpSession httpSession){
        settlementService.createMeterWithPhotoAndInformation(files,infoRequest,httpSession);
        return ResponseEntity
                .ok()
                .body("Images added");
    }

   @GetMapping("/images")
    public List<ImageDto> getImagesWithMeterType (HttpSession httpSession){
        List<Image> images = photoService.getImagesByImageTypeInCurrentDay(httpSession,ImageType.METER);
        List <ImageDto> imageDtos = ImageMapper.mapToImageDtos(images);
        return imageDtos;
    }

    @DeleteMapping("/image/{imageId}")
    public void deleteImage(@PathVariable Long imageId){
        photoService.delete(imageId);
    }

    @GetMapping("/flats")
    public List<Flat> getFlats(HttpSession httpSession){
        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        return flatService.findFlatsByRealEstateId(realEstateId);
    }

    @PostMapping ("/meter")
    public void setupMeter (HttpSession httpSession,@RequestBody MeterRequest meterRequest){
        settlementService.createOrUpdateMeter(meterRequest,httpSession);
    }

    @PostMapping("/media-cost")
    public void createMediaCost (@RequestBody MediaCost mediaCost){
        mediaCostService. createMedia(mediaCost);
    }
    @PostMapping("/additional-cost")
        public void createAdditionalCost(@RequestBody AdditionalCost additionalCost){
        additionalCostService.createAdditionalCost(additionalCost);
    }
    @PostMapping(value = "/images", params = "imageType")
    public ResponseEntity<?> createImages(@RequestParam("images") List<MultipartFile> files,@RequestParam ImageType imageType,HttpSession httpSession){
        photoService.createImages(files,imageType,httpSession);
        return ResponseEntity
                .ok()
                .body("Images added");
    }
}
