package com.example.sensormonitoringserver.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Coord {
    private double x;
    private double y;
    private double z;

    public Coord(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
