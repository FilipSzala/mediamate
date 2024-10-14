package com.mediamate.dto.dashboard;

import com.mediamate.dto.dashboard.components.bar_chart.BarChart;
import com.mediamate.dto.dashboard.components.circular_progress.CircularProgressBarDto;
import com.mediamate.dto.dashboard.components.table.*;
import com.mediamate.dto.header.components.RealEstateAddressesDto;
import com.mediamate.model.cost.additionalCost.AdditionalCost;
import com.mediamate.model.cost.media_cost.MediaCostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
@Getter
@Setter
public class Dashboard {
    private Long flatId;
    private MediaCostDto mediaCostDto;
    private List<TableAllFeesDto> tableAllFeesDtos;
    private List<TableElectricityDto> tableElectricityDtos;
    private CircularProgressBarDto circularProgressBarDto;
    private List<RealEstateAddressesDto> realEstateAddressesDtos;
    private List<TableWaterDto> tableWaterDtos;
    private List<TableGasDto> tableGasDtos;
    private List<TableAdditionalFeesDto> tableAdditionalFees;
    private List<BarChart> barCharts;
    private List<AdditionalCost> additionalCosts;

    public Dashboard(List<TableAllFeesDto> tableAllFeesDtos) {
        this.tableAllFeesDtos = tableAllFeesDtos;
    }
}
