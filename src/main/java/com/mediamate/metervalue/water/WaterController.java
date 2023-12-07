package com.mediamate.metervalue.water;

import com.mediamate.metervalue.water.Water;
import com.mediamate.metervalue.MeterValueService;
import com.mediamate.metervalue.water.WaterService;
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
