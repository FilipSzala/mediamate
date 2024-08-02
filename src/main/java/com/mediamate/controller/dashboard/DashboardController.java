package com.mediamate.controller.dashboard;

import com.mediamate.dto.TableAllFeesDto;
import com.mediamate.model.media_summary.MediaSummaryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private MediaSummaryService mediaSummaryService;

    @Autowired
    public DashboardController(MediaSummaryService mediaSummaryService) {
        this.mediaSummaryService = mediaSummaryService;
    }

    @GetMapping("/all-fees")
    public List<TableAllFeesDto> mediaSummaries(HttpSession httpSession){
        return mediaSummaryService.findLastMediaSummariesLoginUser(httpSession);
    }
}
