package com.mediamate.summary.detaile_summary;
import com.mediamate.cost.mediaCost.CostService;
import com.mediamate.flat.Flat;
import com.mediamate.flat.FlatService;
import com.mediamate.meter.MeterService;
import com.mediamate.meter.MeterType;
import com.mediamate.realestate.RealEstate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component

public class ElectricityConsumption {
    private double lastMeterInFlat;
    private double oneBeforeLastMeterInFlat ;
    private double lastMeterInRealEstate;
    private double oneBeforeLastMeterInRealEstate;

    private FlatService flatService;
    private CostService costService;
    private MeterService meterService;

    @Autowired
    public ElectricityConsumption(FlatService flatService, CostService costService, MeterService meterService) {
        this.flatService = flatService;
        this.costService = costService;
        this.meterService = meterService;
    }

    public double countElectricityConsumption(Long flatId) {
        Flat flat = flatService.findFlatById(flatId);
        RealEstate realEstate = flat.getRealEstate();
        int flatCount = realEstate.getFlats().size();

        this.lastMeterInFlat = meterService.getMeterByFlatIdAndMeterTypeInCurrentMonth(flatId, MeterType.ELECTRICITY).getValue();
        this.oneBeforeLastMeterInFlat = meterService.getMeterByFlatIdAndMeterTypeInOneBeforeLastMonth(flatId, MeterType.ELECTRICITY).getValue();
        this.lastMeterInRealEstate = meterService.getMeterByRealEstateIdAndMeterTypeInCurrentMonth(realEstate.getId(),MeterType.ELECTRICITY).getValue();
        this.oneBeforeLastMeterInRealEstate = meterService.getMeterByRealEstateIdAndMeterTypeInOneBeforeLastMonth(realEstate.getId(),MeterType.ELECTRICITY).getValue();

        double consumptionByFlatWithoutAdministration = lastMeterInFlat - oneBeforeLastMeterInFlat;
        double administrationConsumptionPerFlat = countAdminConsumptionPerFlat(lastMeterInRealEstate,oneBeforeLastMeterInRealEstate,flatCount);
        double totalConsumption = consumptionByFlatWithoutAdministration + administrationConsumptionPerFlat;

        return totalConsumption;
    }

        private double countAdminConsumptionPerFlat(double lastMeterInRealEstate, double oneBeforeLastMeterInRealEstate, int flatCount) {
        double adminConsumptionPerFlat = (lastMeterInRealEstate-oneBeforeLastMeterInRealEstate)/flatCount;
        return adminConsumptionPerFlat;
    }



}
