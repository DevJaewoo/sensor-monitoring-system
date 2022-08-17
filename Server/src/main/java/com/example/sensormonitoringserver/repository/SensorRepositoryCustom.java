package com.example.sensormonitoringserver.repository;

import com.example.sensormonitoringserver.dto.SensorSearch;
import com.example.sensormonitoringserver.entity.Sensor;

import java.util.List;

public interface SensorRepositoryCustom {
    List<Sensor> search(Long clientId, SensorSearch sensorSearch);
}
