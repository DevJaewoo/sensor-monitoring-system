package com.example.sensormonitoringserver.repository;

import com.example.sensormonitoringserver.dto.SensorSearch;
import com.example.sensormonitoringserver.entity.Client;
import com.example.sensormonitoringserver.entity.Coord;
import com.example.sensormonitoringserver.entity.Sensor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SensorRepositoryTest {

    @PersistenceContext EntityManager em;
    @Autowired SensorRepository sensorRepository;

    @Test
    public void save() {
        //given
        Client client = new Client("client1");
        em.persist(client);

        Sensor sensor1 = new Sensor(client, 1, 1, 3, new Coord());
        Sensor sensor2 = new Sensor(client, 1, 2, 3, new Coord());
        Sensor sensor3 = new Sensor(client, 1, 3, 3, new Coord());
        sensorRepository.save(sensor1);
        sensorRepository.save(sensor2);
        sensorRepository.save(sensor3);

        //when
        List<Sensor> result = sensorRepository.findAll();

        //then
        assertThat(result).extracting("eco2").containsExactly(1, 2, 3);
    }

    @Test
    public void search() throws Exception {
        //given
        Client client = new Client("client1");
        em.persist(client);

        LocalDateTime from = LocalDateTime.now();
        Thread.sleep(5);

        Sensor sensor1 = new Sensor(client, 1, 1, 3, new Coord());
        Sensor sensor2 = new Sensor(client, 1, 2, 3, new Coord());
        Sensor sensor3 = new Sensor(client, 1, 3, 3, new Coord());
        sensorRepository.save(sensor1);
        sensorRepository.save(sensor2);

        Thread.sleep(5);
        LocalDateTime to = LocalDateTime.now();
        Thread.sleep(5);

        sensorRepository.save(sensor3);


        //when
        List<Sensor> result = sensorRepository.search(client.getId(), new SensorSearch(from, to));

        //then
        assertThat(result).extracting("eco2").containsExactly(1, 2);
    }
}