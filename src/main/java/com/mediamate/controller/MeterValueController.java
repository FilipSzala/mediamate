package com.mediamate.controller;

import com.mediamate.model.Flat;
import com.mediamate.model.MeterValue;
import com.mediamate.model.Water;
import com.mediamate.service.FlatService;
import com.mediamate.service.MeterValueService;
import com.mediamate.service.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;

@RestController
@RequestMapping("meter-value")
public class MeterValueController {
    MeterValueService meterValueService;
    FlatService flatService;

    WaterService waterService;

    @Autowired
    public MeterValueController(MeterValueService meterValueService, FlatService flatService, WaterService waterService) {
        this.meterValueService = meterValueService;
        this.flatService = flatService;
        this.waterService = waterService;

    }

    @PostMapping("/{id}")
    public void addMeterValue (@PathVariable ("id") Long flatId, @RequestBody MeterValue meterValue,Water water){
        waterService.createWater(water);
        meterValue.setWater(water);
        meterValueService.addMeterValue(meterValue);
        flatService.addMeterValueToMap(flatId,meterValue);
    }
}
