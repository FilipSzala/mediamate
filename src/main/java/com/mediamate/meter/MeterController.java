package com.mediamate.meter;

import com.mediamate.meter.water.Water;
import com.mediamate.flat.FlatService;
import com.mediamate.meter.water.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("meter")
public class MeterController {
    MeterService meterService;
    FlatService flatService;
    WaterService waterService;

    @Autowired
    public MeterController(MeterService meterService, FlatService flatService, WaterService waterService) {
        this.meterService = meterService;
        this.flatService = flatService;
        this.waterService = waterService;

    }

    @PostMapping("/{id}")
    public void createMeter (@PathVariable ("id") Long flatId, @RequestBody Meter meter, Water water){
        meter.setWater(water);
        meterService.createMeter(meter,flatId);
    }
}
