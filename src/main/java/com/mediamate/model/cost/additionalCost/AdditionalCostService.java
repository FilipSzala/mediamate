package com.mediamate.model.cost.additionalCost;

import com.mediamate.model.cost.Cost;
import com.mediamate.model.real_estate.RealEstate;
import com.mediamate.model.real_estate.RealEstateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class AdditionalCostService {
    @Autowired
    HttpSession httpSession;

    RealEstateService realEstateService;

    public AdditionalCostService(RealEstateService realEstateService) {
        this.realEstateService = realEstateService;
    }


}
