package com.mediamate.controller.summary;

import com.mediamate.model.media_summary.MediaSummaryService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("summaries")
@Hidden
public class SummaryController {
    private MediaSummaryService mediaSummaryService;
    @Autowired
    public SummaryController(MediaSummaryService mediaSummaryService) {
        this.mediaSummaryService = mediaSummaryService;
    }
    //TODO :  Method from this controller should be inside settlement controller, shoudn't has own class.
    @PostMapping
    public ResponseEntity<Void> createMediaSummaries(HttpSession httpSession) {
        mediaSummaryService.createMediaSummaries(httpSession);
        return ResponseEntity.ok().build();
    }
}
