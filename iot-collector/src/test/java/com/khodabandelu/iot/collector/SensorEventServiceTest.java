package com.khodabandelu.iot.collector;

import com.khodabandelu.iot.collector.api.queries.AggregateType;
import com.khodabandelu.iot.collector.api.queries.FindOneAggregateQuery;
import com.khodabandelu.iot.collector.domain.SensorEvent;
import com.khodabandelu.iot.collector.domain.SensorEventKey;
import com.khodabandelu.iot.collector.service.SensorEventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class SensorEventServiceTest {

    @Autowired
    private SensorEventService sensorEventService;


    @BeforeTestClass
    public void storeEvents() {
        var s1 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s1").groupId("g1").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime()).build())
                .name("sensor 1 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s2 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s1").groupId("g1").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime()).build())
                .name("sensor 1 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s3 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s3").groupId("g1").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime()).build())
                .name("sensor 3 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s4 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s1").groupId("g2").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime()).build())
                .name("sensor 1 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s5 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s5").groupId("g2").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime()).build())
                .name("sensor 5 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s6 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s1").groupId("g3").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime()).build())
                .name("sensor 1 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s7 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s1").groupId("g4").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime()).build())
                .name("sensor 1 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s8 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s8").groupId("g4").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime()).build())
                .name("sensor 8 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s9 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s9").groupId("g5").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime()).build())
                .name("sensor 9 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s10 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s10").groupId("g6").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime()).build())
                .name("sensor 10 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        List<SensorEvent> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        list.add(s5);
        list.add(s6);
        list.add(s7);
        list.add(s8);
        list.add(s9);
        list.add(s10);
        sensorEventService.saveAll(list);
    }

    @Test
    public void testAggregateBySensorIdMaxSuccess() {
        var query = FindOneAggregateQuery.builder()
                .sensorId("s1")
                .groupId("g1")
                .aggregateType(AggregateType.max)
                .fromDate(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime())
                .toDate(Timestamp.from(Instant.now()).getTime())
                .build();
        var result = sensorEventService.aggregateEvents(query);
        assertThat(result).isNotNull();
    }

    @Test
    public void testAggregateBySensorIdMinSuccess() {
        var query = FindOneAggregateQuery.builder()
                .sensorId("s1")
                .groupId("g1")
                .aggregateType(AggregateType.min)
                .fromDate(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime())
                .toDate(Timestamp.from(Instant.now()).getTime())
                .build();
        var result = sensorEventService.aggregateEvents(query);
        assertThat(result).isNotNull();
    }

    @Test
    public void testAggregateBySensorIdAvgSuccess() {
        var query = FindOneAggregateQuery.builder()
                .sensorId("s1")
                .groupId("g1")
                .aggregateType(AggregateType.avg)
                .fromDate(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime())
                .toDate(Timestamp.from(Instant.now()).getTime())
                .build();
        var result = sensorEventService.aggregateEvents(query);
        assertThat(result).isNotNull();
    }

    @Test
    public void testAggregateBySensorIdMedianSuccess() {
        var query = FindOneAggregateQuery.builder()
                .sensorId("s1")
                .groupId("g1")
                .aggregateType(AggregateType.median)
                .fromDate(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime())
                .toDate(Timestamp.from(Instant.now()).getTime())
                .build();
        var result = sensorEventService.aggregateEvents(query);
        assertThat(result).isNotNull();
    }

    @Test
    public void testAggregateByGroupIdMaxSuccess() {
        var query = FindOneAggregateQuery.builder()
                .sensorId("s1")
                .groupId("g1")
                .aggregateType(AggregateType.max)
                .fromDate(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime())
                .toDate(Timestamp.from(Instant.now()).getTime())
                .build();
        var result = sensorEventService.aggregateEvents(query);
        assertThat(result).isNotNull();
    }

    @Test
    public void testAggregateByGroupIdMinSuccess() {
        var query = FindOneAggregateQuery.builder()
                .sensorId("s1")
                .groupId("g1")
                .aggregateType(AggregateType.min)
                .fromDate(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime())
                .toDate(Timestamp.from(Instant.now()).getTime())
                .build();
        var result = sensorEventService.aggregateEvents(query);
        assertThat(result).isNotNull();
    }

    @Test
    public void testAggregateByGroupIdAvgSuccess() {
        var query = FindOneAggregateQuery.builder()
                .sensorId("s1")
                .groupId("g1")
                .aggregateType(AggregateType.avg)
                .fromDate(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime())
                .toDate(Timestamp.from(Instant.now()).getTime())
                .build();
        var result = sensorEventService.aggregateEvents(query);
        assertThat(result).isNotNull();
    }

    @Test
    public void testAggregateByGroupIdMedianSuccess() {
        var query = FindOneAggregateQuery.builder()
                .sensorId("s1")
                .groupId("g1")
                .aggregateType(AggregateType.median)
                .fromDate(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime())
                .toDate(Timestamp.from(Instant.now()).getTime())
                .build();
        var result = sensorEventService.aggregateEvents(query);
        assertThat(result).isNotNull();
    }

}
