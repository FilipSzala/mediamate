package com.mediamate.model.real_estate;

import com.mediamate.model.flat.Flat;
import com.mediamate.model.flat.FlatService;
import com.mediamate.model.user.User;
import com.mediamate.config.security.SecurityService;
import com.mediamate.model.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RealEstateService {
    @Autowired
    private RealEstateRepository realEstateRepository;
    private SecurityService securityService;
    @Autowired
    @Lazy
    private  FlatService flatService;

    UserService userService;
    public RealEstateService(SecurityService securityService,UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }

    public Optional<RealEstate> findById(Long id){
        return realEstateRepository.findById(id);
    }
    public List<RealEstate> findAllByUserId(Long id){

        return realEstateRepository.findByUserId(id);
    }
    public RealEstate findRealEstateByHttpSession (HttpSession httpSession){
        Long realEstateId = (Long) httpSession.getAttribute("chosenRealEstateId");
        RealEstate realEstate = findById(realEstateId).orElseThrow();
        return realEstate;
    }
    public void updateRealEstate(RealEstate modifiedRealEstate) {
        realEstateRepository.save(modifiedRealEstate);
    }


    public List <RealEstateDto> findAllByLogInUser() {
        User user = securityService.findUserBySession();
        List<RealEstate> realEstates = findAllByUserId(user.getId());
        List<RealEstateDto> realEstateDtos = RealEstateMapper.mapToRealEstateDtos(realEstates);
        return realEstateDtos;
    }

}
