package com.example.sensormonitoringserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorCreateRequestDto {

    private double temp;
    private int eco2;
    private int tvoc;
    private CoordDto accel;
}
