package com.mediamate.summary.detaile_summary;

import com.mediamate.summary.SummaryAbstract;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@Getter
@Setter

public class ElectricitySummary extends SummaryAbstract {
    Double meterValue;
    Double administrationMeterValue;
    Double flatConsumption;
    Double administrationConsumption;





}
