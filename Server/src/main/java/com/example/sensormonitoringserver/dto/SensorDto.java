package com.example.sensormonitoringserver.dto;

import com.example.sensormonitoringserver.entity.Sensor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class SensorDto {
    private int eco2;
    private int tvoc;
    private double temp;
    private CoordDto accel;
    private LocalDateTime createdDate;

    public SensorDto(Sensor sensor) {
        this.eco2 = sensor.getEco2();
        this.tvoc = sensor.getTvoc();
        this.temp = sensor.getTemp();
        this.accel = new CoordDto(sensor.getAccel());
        this.createdDate = sensor.getCreatedDate();
    }

    @Data
    @AllArgsConstructor
    public static class ResponseList {
        private List<SensorDto> sensorData;
    }
}
