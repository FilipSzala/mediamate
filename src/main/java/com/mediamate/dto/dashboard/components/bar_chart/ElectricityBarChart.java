package com.mediamate.dto.dashboard.components.bar_chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ElectricityBarChart {
    private LocalDate createdAt;
    private double value;
}
