package com.mediamate.summary;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("summary")
public class MediaSummaryController {
    private MediaSummaryService mediaSummaryService;
    @Autowired
    public MediaSummaryController(MediaSummaryService mediaSummaryService) {
        this.mediaSummaryService = mediaSummaryService;
    }

  /*  @GetMapping("/{date}")
    public List<MediaSummary> getSummaries (HttpSession httpSession, @PathVariable LocalDate date){
        return mediaSummaryService.generateFlatSummaries(httpSession,date);
    }*/
}
