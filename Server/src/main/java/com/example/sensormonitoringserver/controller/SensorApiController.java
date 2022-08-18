package com.example.sensormonitoringserver.controller;

import com.example.sensormonitoringserver.dto.SensorCreateRequestDto;
import com.example.sensormonitoringserver.dto.SensorDto;
import com.example.sensormonitoringserver.dto.SensorSearch;
import com.example.sensormonitoringserver.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class SensorApiController {

    private final SensorService sensorService;

    @PostMapping("/{id}/sensor")
    public ResponseEntity<?> createSensorData(@PathVariable Long id, @Valid @RequestBody SensorCreateRequestDto sensorCreateRequestDTO) {
        sensorService.uploadSensorData(id, sensorCreateRequestDTO);
        return ResponseEntity.ok("");
    }

    @GetMapping("/{id}/sensor")
    public ResponseEntity<?> readSensorData(@PathVariable Long id, SensorSearch sensorSearch, Pageable pageable) {
        List<SensorDto> result = sensorService.searchSensorData(id, sensorSearch, pageable);
        return ResponseEntity.ok(new SensorDto.ResponseList(result));
    }
}
