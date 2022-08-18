package com.example.sensormonitoringserver.repository;

import com.example.sensormonitoringserver.dto.SensorSearch;
import com.example.sensormonitoringserver.entity.Sensor;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SensorRepositoryCustom {
    List<Sensor> search(Long clientId, SensorSearch sensorSearch, Pageable pageable);
}
