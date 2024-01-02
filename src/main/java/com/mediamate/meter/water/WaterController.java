package com.mediamate.meter.water;

import com.mediamate.meter.MeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("water")
public class WaterController {
    private WaterService waterService;
    private MeterService meterService;
    @Autowired
    public WaterController(WaterService waterService, MeterService meterService) {
        this.waterService = waterService;
        this.meterService = meterService;
    }

    @PutMapping("/{id}")
    public void updateWater (@PathVariable ("id") Long meterValueId, @RequestBody Water updatedWater) {
        waterService.updateWater(meterValueId,updatedWater);
    }
}
