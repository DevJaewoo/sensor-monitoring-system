package com.example.sensormonitoringserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SensorCreateRequestDto {

    private double temp;
    private int eco2;
    private int tvoc;
    private CoordDto accel;
}
