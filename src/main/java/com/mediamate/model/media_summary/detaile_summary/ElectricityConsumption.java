package com.mediamate.model.media_summary.detaile_summary;
import com.mediamate.model.cost.CostService;
import com.mediamate.model.flat.Flat;
import com.mediamate.model.flat.FlatService;
import com.mediamate.model.meter.MeterService;
import com.mediamate.model.meter.MeterType;
import com.mediamate.model.real_estate.RealEstate;
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
    private int flatCount;

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
        flatCount = realEstate.getFlats().size();

        this.lastMeterInFlat = meterService.getMeterByFlatIdAndMeterTypeInCurrentMonth(flatId, MeterType.ELECTRICITY).getValue();
        this.oneBeforeLastMeterInFlat = meterService.getMeterByFlatIdAndMeterTypeInOneBeforeLastMonth(flatId, MeterType.ELECTRICITY).getValue();
        this.lastMeterInRealEstate = meterService.getMeterByRealEstateIdAndMeterTypeInCurrentMonth(realEstate.getId(),MeterType.ELECTRICITY).getValue();
        this.oneBeforeLastMeterInRealEstate = meterService.getMeterByRealEstateIdAndMeterTypeInOneBeforeLastMonth(realEstate.getId(),MeterType.ELECTRICITY).getValue();

        double consumptionByFlatWithoutAdministration = lastMeterInFlat - oneBeforeLastMeterInFlat;
        double administrationConsumptionPerFlat = countAdminConsumptionPerFlat();
        double totalConsumption = consumptionByFlatWithoutAdministration + administrationConsumptionPerFlat;

        return totalConsumption;
    }

        public double countAdminConsumptionPerFlat() {
        double adminConsumptionPerFlat = (this.lastMeterInRealEstate-this.oneBeforeLastMeterInRealEstate)/flatCount;
        return adminConsumptionPerFlat;
    }
        public double getLastMeterReadingInFlat(){
        return lastMeterInFlat;
    }
        public double getLastMeterReadingInAdministration(){
        return lastMeterInRealEstate;
        }

}
