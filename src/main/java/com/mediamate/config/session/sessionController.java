package com.mediamate.config.session;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("session")
@Hidden
public class sessionController {
    @PostMapping("/choose-real-estate")
    public void chooseRealEstate(@RequestParam Long realEstateId, HttpSession session) {
        session.setAttribute("chosenRealEstateId", realEstateId);
    }
}
