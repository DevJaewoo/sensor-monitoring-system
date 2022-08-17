package com.example.sensormonitoringserver.repository;

import com.example.sensormonitoringserver.dto.SensorSearch;
import com.example.sensormonitoringserver.entity.Sensor;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.sensormonitoringserver.entity.QClient.*;
import static com.example.sensormonitoringserver.entity.QSensor.*;

@Repository
@RequiredArgsConstructor
public class SensorRepositoryImpl implements SensorRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Sensor> search(Long clientId, SensorSearch sensorSearch) {

        return queryFactory
                .selectFrom(sensor)
                .leftJoin(sensor.client, client)
                .where(
                        clientIdEq(clientId),
                        dateAfter(sensorSearch.getFrom()),
                        dateBefore(sensorSearch.getTo())
                )
                .fetch();
    }

    private BooleanExpression clientIdEq(Long id) {
        return id != null ? sensor.client.id.eq(id) : null;
    }

    private BooleanExpression dateBefore(LocalDateTime dateTime) {
        return dateTime != null ? sensor.createdDate.before(dateTime) : null;
    }

    private BooleanExpression dateAfter(LocalDateTime dateTime) {
        return dateTime != null ? sensor.createdDate.after(dateTime) : null;
    }
}
