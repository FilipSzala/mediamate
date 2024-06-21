package com.mediamate.config.security.session;

import com.mediamate.model.real_estate.RealEstateDto;
import com.mediamate.config.security.SecurityService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("session")
public class sessionController {
    SecurityService securityService;
    @Autowired
    public sessionController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/real-estates")
        public List<RealEstateDto> allRealEstatesDtoLogInUser(){
        return securityService.getRealEstatesDtoBySession();
    }
    @PostMapping("/choose-real-estate")
    public void chooseRealEstate(@RequestParam Long realEstateId, HttpSession session) {
        session.setAttribute("chosenRealEstateId", realEstateId);
    }
}
