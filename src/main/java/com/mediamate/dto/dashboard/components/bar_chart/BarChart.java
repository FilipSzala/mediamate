package com.mediamate.dto.dashboard.components.bar_chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BarChart {
    private LocalDate createdAt;
    private double value;
    private BarChartType barChartType;
}
