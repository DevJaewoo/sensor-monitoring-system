package com.example.sensormonitoringserver.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Client {

    @Id @GeneratedValue
    @Column(name = "client_id")
    private Long id;

    private String name;
}
