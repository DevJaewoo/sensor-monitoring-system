package com.example.sensormonitoringserver.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sensor {

    @Id @GeneratedValue
    @Column(name = "sensor_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    private double temp;
    private int eco2;
    private int tvoc;

    @Embedded
    private Coord accel = new Coord();

    private LocalDateTime createdDate;

    @PrePersist
    private void prePersist() {
        createdDate = LocalDateTime.now();
    }

    public Sensor(Client client, double temp, int eco2, int tvoc, Coord accel) {
        this.client = client;
        this.temp = temp;
        this.eco2 = eco2;
        this.tvoc = tvoc;
        this.accel = accel;
    }
}
