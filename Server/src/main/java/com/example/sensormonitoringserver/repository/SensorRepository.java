package com.example.sensormonitoringserver.repository;

import com.example.sensormonitoringserver.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
