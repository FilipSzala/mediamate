package com.mediamate.meterValue.water;

import com.mediamate.meterValue.MeterValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("water")
public class WaterController {
    private WaterService waterService;
    private MeterValueService meterValueService;
    @Autowired
    public WaterController(WaterService waterService, MeterValueService meterValueService) {
        this.waterService = waterService;
        this.meterValueService = meterValueService;
    }

    @PutMapping("/{id}")
    public void updateWater (@PathVariable ("id") Long meterValueId, @RequestBody Water updatedWater) {
        waterService.updateWater(meterValueId,updatedWater);
    }
}
