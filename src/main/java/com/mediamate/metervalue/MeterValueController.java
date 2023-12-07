package com.mediamate.metervalue;

import com.mediamate.metervalue.MeterValue;
import com.mediamate.metervalue.water.Water;
import com.mediamate.flat.FlatService;
import com.mediamate.metervalue.MeterValueService;
import com.mediamate.metervalue.water.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
