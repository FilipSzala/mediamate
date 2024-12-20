package com.mediamate.controller.settlement;

import com.mediamate.controller.dashboard.sms_api.SmsSenderService;
import com.mediamate.controller.settlement.request.GasInvoiceDetails;
import com.mediamate.controller.settlement.response.GasPriceWithDistribution;
import com.mediamate.model.cost.Cost;
import com.mediamate.model.cost.CostService;
import com.mediamate.model.cost.additionalCost.AdditionalCost;
import com.mediamate.model.cost.additionalCost.AdditionalCostService;
import com.mediamate.model.cost.media_cost.MediaCost;
import com.mediamate.model.flat.FlatService;
import com.mediamate.model.image.ImageService;
import com.mediamate.model.image.ImageType;
import com.mediamate.model.image.request.ImageInformationRequest;
import com.mediamate.model.media_summary.MediaSummaryService;
import com.mediamate.model.real_estate.RealEstateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collections;
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
    private SmsSenderService smsSenderService;

    @Autowired
    public SettlementController(ImageService imageService, SettlementService settlementService, FlatService flatService, AdditionalCostService additionalCostService, MediaSummaryService mediaSummaryService, RealEstateService realEstateService, CostService costService, SmsSenderService smsSenderService) {
        this.imageService = imageService;
        this.settlementService = settlementService;
        this.flatService = flatService;
        this.additionalCostService = additionalCostService;
        this.mediaSummaryService = mediaSummaryService;
        this.realEstateService = realEstateService;
        this.costService = costService;
        this.smsSenderService = smsSenderService;
    }

    //Methods in this class depend on Real Estate's session.Therefore, if I coded method named "getFlats" it means that this method returns
    //all Flats by realEstateId from session.
    @PostMapping ("/meters/{userAcceptUnusunalMeterValue}")
    public ResponseEntity<?> createMeterWithPhotoAndInformation(@RequestParam ("files") List<MultipartFile> files, @RequestPart("infoRequest") List <ImageInformationRequest> infoRequest, HttpSession httpSession, @PathVariable boolean userAcceptUnusunalMeterValue){
        List<String> validationMessages = settlementService.createMetersAndImages(files,infoRequest,httpSession, userAcceptUnusunalMeterValue);
        boolean validationCorrect = validationMessages.isEmpty();
        if(validationCorrect) {
            return ResponseEntity
                    .ok()
                    .body("Meters added");
        }
        else {
            return ResponseEntity
                    .badRequest()
                    .body(validationMessages);
        }
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
            try {
                imageService.createImages(files, Collections.emptyList(), ImageType.INVOICE, httpSession);
                return ResponseEntity.ok("Invoice images added");
            }  catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
            }
        }
    @PostMapping ("/inspection")
    public ResponseEntity createInspectionImages (@RequestParam ("files") List<MultipartFile> files,@RequestParam("expiryDates") List<String> expiryDates,HttpSession httpSession){
        imageService.createImages(files,expiryDates, ImageType.INSPECTION,httpSession);
        return ResponseEntity
                .ok()
                .body("Inspection images added");
    }
    @PostMapping ("/gas-price")
    public GasPriceWithDistribution calculateGasPriceWithDistribiuton (@RequestBody GasInvoiceDetails gasInvoiceDetails){
        return settlementService.calculateGasPrice(gasInvoiceDetails);
    }
}
