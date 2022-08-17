package com.example.sensormonitoringserver.service;

import com.example.sensormonitoringserver.dto.SensorCreateRequestDto;
import com.example.sensormonitoringserver.dto.SensorDto;
import com.example.sensormonitoringserver.dto.SensorSearch;
import com.example.sensormonitoringserver.entity.Client;
import com.example.sensormonitoringserver.entity.Coord;
import com.example.sensormonitoringserver.entity.Sensor;
import com.example.sensormonitoringserver.repository.ClientRepository;
import com.example.sensormonitoringserver.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final ClientRepository clientRepository;
    private final SensorRepository sensorRepository;

    @Transactional
    public void uploadSensorData(Long clientId, SensorCreateRequestDto createRequestDto) {

        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NoSuchElementException("ID가 " + clientId + "인 사용자가 존재하지 않습니다."));

        Sensor sensor = new Sensor(
                client,
                createRequestDto.getTemp(),
                createRequestDto.getEco2(),
                createRequestDto.getTvoc(),
                new Coord(
                        createRequestDto.getAccel().getX(),
                        createRequestDto.getAccel().getY(),
                        createRequestDto.getAccel().getZ()
                )
        );

        sensorRepository.save(sensor);
    }

    public List<SensorDto> searchSensorData(Long clientId, SensorSearch sensorSearchDto) {
        return sensorRepository.search(clientId, sensorSearchDto).stream()
                .map(SensorDto::new)
                .collect(Collectors.toList());
    }
}
