package com.mediamate.controller.realestate_selector;

import com.mediamate.model.real_estate.RealEstateDto;
import com.mediamate.model.real_estate.RealEstateService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("selector")
@NoArgsConstructor
public class RealEstateSelectorController {

    private RealEstateService realEstateService;

    @Autowired
    public RealEstateSelectorController(RealEstateService realEstateService) {
        this.realEstateService = realEstateService;
    }

    @GetMapping()
    public List<RealEstateDto> getRealEstateLoginUser (){
        return realEstateService.findAllByLogInUser();
    }

}
