package com.example.sensormonitoringserver.repository;

import com.example.sensormonitoringserver.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
