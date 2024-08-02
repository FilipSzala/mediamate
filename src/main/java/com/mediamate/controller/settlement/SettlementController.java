package com.mediamate.controller.settlement;

import com.mediamate.model.media_summary.MediaSummaryService;
import com.mediamate.model.cost.Cost;
import com.mediamate.model.cost.CostService;
import com.mediamate.model.cost.additionalCost.AdditionalCost;
import com.mediamate.model.cost.additionalCost.AdditionalCostService;
import com.mediamate.model.cost.media_cost.MediaCost;
import com.mediamate.model.flat.FlatService;
import com.mediamate.model.image.ImageService;
import com.mediamate.model.image.ImageType;
import com.mediamate.model.image.request.ImageInformationRequest;
import com.mediamate.model.real_estate.RealEstateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("settlement")
public class SettlementController {
    private ImageService imageService;
    private SettlementService settlementService;
    private FlatService flatService;
    private AdditionalCostService additionalCostService;
    private MediaSummaryService mediaSummaryService;
    private RealEstateService realEstateService;
    private CostService costService;


    @Autowired
    public SettlementController(ImageService imageService, SettlementService settlementService, FlatService flatService, AdditionalCostService additionalCostService, MediaSummaryService mediaSummaryService, RealEstateService realEstateService, CostService costService) {
        this.imageService = imageService;
        this.settlementService = settlementService;
        this.flatService = flatService;
        this.additionalCostService = additionalCostService;
        this.mediaSummaryService = mediaSummaryService;
        this.realEstateService = realEstateService;
        this.costService = costService;
    }

    //Methods in this class depend on Real Estate's session.Therefore, if I coded method named "getFlats" it means that this method returns
    //all Flats by realEstateId from session.
    @GetMapping ()
    public List <Long> getFlatsIdBySession(HttpSession httpSession){
        List<Long> flatsId = settlementService.getFlatsIdBySession(httpSession);
        return flatsId;
    }
    @PostMapping ("/meters")
    public ResponseEntity<?> createMeterWithPhotoAndInformation(@RequestParam ("files") List<MultipartFile> files, @RequestPart("infoRequest") List <ImageInformationRequest> infoRequest, HttpSession httpSession){
        settlementService.createMeterWithPhotoAndInformation(files,infoRequest,httpSession);
        return ResponseEntity
                .ok()
                .body("Meters added");
    }
    @GetMapping ("media-cost")
    public Cost getLastMediaCost(HttpSession httpSession){
        return costService.findLastMediaCost(httpSession);
    }

    @PostMapping("/media-cost")
    public void createMediaCost (@RequestBody MediaCost mediaCost,HttpSession httpSession){
        costService.createMediaCost(mediaCost,httpSession);
    }

    @GetMapping ("/additional-cost")
    public List<Cost> getLastAdditionalCost (HttpSession httpSession){
        return costService.findAdditionalCostByRealEstateIdInLastMonth(httpSession, LocalDate.now());
    }

    @PostMapping("/additional-cost")
        public void createAdditionalCost(@RequestBody List <AdditionalCost> additionalCost, HttpSession httpSession){
        costService.createAdditionalCosts(additionalCost,httpSession);
    }


    @PostMapping ("/invoice")
    public ResponseEntity createInvoiceImages (@RequestParam ("files") List<MultipartFile> files,HttpSession httpSession){
        imageService.createImages(files, ImageType.INVOICE,httpSession);
        return ResponseEntity
                .ok()
                .body("Invoice images added");
    }

    @PostMapping ("/inspection")
    public ResponseEntity createInspectionImages (@RequestParam ("files") List<MultipartFile> files,HttpSession httpSession){
        imageService.createImages(files, ImageType.INSPECTION,httpSession);
        return ResponseEntity
                .ok()
                .body("Inspection images added");
    }

}
