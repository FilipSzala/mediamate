package com.mediamate.controller;

import com.mediamate.model.Flat;
import com.mediamate.model.MeterValue;
import com.mediamate.service.FlatService;
import com.mediamate.service.MeterValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;

@RestController
@RequestMapping("meter-value")
public class MeterValueController {
    MeterValueService meterValueService;

    @Autowired
    public MeterValueController(MeterValueService meterValueService, FlatService flatService) {
        this.meterValueService = meterValueService;
    }
    @PostMapping("/{id}")
    public void addMeterValue (@PathVariable ("id") Long flatId, @RequestBody MeterValue meterValue){
        meterValueService.addMeterValue(flatId,meterValue);
    }
}
