package com.mediamate.summary;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("summary")
public class SummaryController {
    private  SummaryService summaryService;
    @Autowired
    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/{date}")
    public List<Summary> getSummaries (HttpSession httpSession,@PathVariable LocalDate date){
        return summaryService.generateFlatSummaries(httpSession,date);
    }
}
