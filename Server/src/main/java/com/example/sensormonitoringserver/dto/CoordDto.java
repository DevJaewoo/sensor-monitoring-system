package com.example.sensormonitoringserver.dto;

import com.example.sensormonitoringserver.entity.Coord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordDto {
    private double x;
    private double y;
    private double z;

    public CoordDto(Coord coord) {
        this.x = coord.getX();
        this.y = coord.getY();
        this.z = coord.getZ();
    }
}
