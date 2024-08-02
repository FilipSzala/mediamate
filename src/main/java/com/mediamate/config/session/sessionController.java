package com.mediamate.config.session;

import com.mediamate.model.real_estate.RealEstateDto;
import com.mediamate.config.security.SecurityService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("session")
@Hidden
public class sessionController {
    SecurityService securityService;
    @Autowired
    public sessionController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/choose-real-estate")
    public void chooseRealEstate(@RequestParam Long realEstateId, HttpSession session) {
        session.setAttribute("chosenRealEstateId", realEstateId);
    }
}
