package com.mediamate.service;

import com.mediamate.model.Water;
import com.mediamate.repository.WaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaterService {
    @Autowired
    WaterRepository waterRepository;


    public void createWater (Water water){
        waterRepository.save(water);
    }
}
