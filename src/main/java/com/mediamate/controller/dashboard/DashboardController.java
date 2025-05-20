package com.mediamate.controller.dashboard;

import com.mediamate.controller.dashboard.sms_api.SmsSenderService;
import com.mediamate.dto.dashboard.Dashboard;
import com.mediamate.dto.header.Header;
import com.mediamate.model.media_summary.MediaSummaryService;
import com.mediamate.model.real_estate.RealEstateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private MediaSummaryService mediaSummaryService;
    private DashboardService dashboardService;
    private RealEstateService realEstateService;
    private SmsSenderService smsSenderService;

    @Autowired
    public DashboardController(MediaSummaryService mediaSummaryService, DashboardService dashboardService, RealEstateService realEstateService, SmsSenderService smsSenderService) {
        this.mediaSummaryService = mediaSummaryService;
        this.dashboardService = dashboardService;
        this.realEstateService = realEstateService;
        this.smsSenderService = smsSenderService;
    }
    @GetMapping("")
    public Header getHeaderData(HttpSession httpSession){
        return dashboardService.findHeaderData(httpSession);
    }
    @GetMapping("/all-fees")
    public Dashboard getDashboardData(HttpSession httpSession){
         return dashboardService.findDashboardDataForLoginAccount(httpSession);
    }
    @GetMapping ("sms-raport")
    public ResponseEntity<String> sendSmsWithMediaSummary(HttpSession httpSession) {
        try {
            dashboardService.sendSmsWithMediaSummary(httpSession);
            return ResponseEntity.ok().body("Sms sended succesfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while sending sms : " + e.getMessage());
        }
    }

}
