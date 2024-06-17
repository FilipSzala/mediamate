package com.mediamate.summary;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("summaries")
public class SummaryController {
    private SummaryService summaryService;
    @Autowired
    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }
    @PostMapping()
    public void generateSummaries (HttpSession httpSession){
        summaryService.createMediaSummaries(httpSession);
    }
}
