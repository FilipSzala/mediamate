package com.mediamate.controller.dashboard;

import com.mediamate.dto.dashboard.Dashboard;
import com.mediamate.dto.header.Header;
import com.mediamate.model.media_summary.MediaSummaryService;
import com.mediamate.model.real_estate.RealEstateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private MediaSummaryService mediaSummaryService;
    private DashboardService dashboardService;
    private RealEstateService realEstateService;

    @Autowired
    public DashboardController(MediaSummaryService mediaSummaryService, DashboardService dashboardService, RealEstateService realEstateService) {
        this.mediaSummaryService = mediaSummaryService;
        this.dashboardService = dashboardService;
        this.realEstateService = realEstateService;
    }
    @GetMapping("")
    public Header getHeaderData(HttpSession httpSession){
        return dashboardService.findHeaderData(httpSession);
    }
    @GetMapping("/all-fees")
    public Dashboard getDashboardData(HttpSession httpSession){
         return dashboardService.findDashboardDataForLoginAccount(httpSession);
    }

}
