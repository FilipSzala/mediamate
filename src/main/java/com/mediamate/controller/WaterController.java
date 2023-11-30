package com.mediamate.controller;

import com.mediamate.model.Water;
import com.mediamate.service.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("water")
public class WaterController {
    private WaterService waterService;
    @Autowired
    public WaterController(WaterService waterService) {
        this.waterService = waterService;
    }

    @PostMapping("/{id}")
    public void createWater (@PathVariable ("id") Long materValueId, @RequestBody Water water) {

        waterService.createWater(water);
    }
}
