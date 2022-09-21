package com.khodabandelu.iot.collector;

import com.khodabandelu.iot.collector.api.queries.AggregateType;
import com.khodabandelu.iot.collector.domain.SensorEvent;
import com.khodabandelu.iot.collector.domain.SensorEventKey;
import com.khodabandelu.iot.collector.service.SensorEventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class SensorEventControllerTest {

    @Autowired
    private SensorEventService sensorEventService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeTestClass
    public void storeEvents() {
        var s1 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s1").groupId("g1").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime()).build())
                .name("sensor 1 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s2 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s1").groupId("g1").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:13.250202600Z")).getTime()).build())
                .name("sensor 1 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s3 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s3").groupId("g1").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:14.250202600Z")).getTime()).build())
                .name("sensor 3 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s4 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s1").groupId("g2").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:15.250202600Z")).getTime()).build())
                .name("sensor 1 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s5 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s5").groupId("g2").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:16.250202600Z")).getTime()).build())
                .name("sensor 5 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s6 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s1").groupId("g3").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:17.250202600Z")).getTime()).build())
                .name("sensor 1 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s7 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s1").groupId("g4").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:18.250202600Z")).getTime()).build())
                .name("sensor 1 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s8 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s8").groupId("g4").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:19.250202600Z")).getTime()).build())
                .name("sensor 8 ")
                .topic("topic 1")
                .value(new Random().nextLong(50, 100))
                .build();
        var s9 = SensorEvent.builder()
                .id(SensorEventKey.builder().sensorId("s9").groupId("g5").timestamp(Timestamp.from(Instant.parse("2022-08-20T08:44:21.250202600Z")).getTime()).build())
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

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    public void testAggregateBySensorIdMaxSuccess() throws Exception {
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("sensorId", "s1");
        paramsMap.add("groupId", "g1");
        paramsMap.add("aggregateType", AggregateType.max.name());
        paramsMap.add("fromDate", Long.toString(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime()));
        paramsMap.add("toDate", Long.toString(Timestamp.from(Instant.now()).getTime()));

        this.mockMvc.perform(get("/api/sensorEvent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.aggregateValue").isNotEmpty())
                .andReturn();
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    public void testAggregateBySensorIdMinSuccess() throws Exception {
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("sensorId", "s1");
        paramsMap.add("groupId", "g1");
        paramsMap.add("aggregateType", AggregateType.min.name());
        paramsMap.add("fromDate", Long.toString(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime()));
        paramsMap.add("toDate", Long.toString(Timestamp.from(Instant.now()).getTime()));

        this.mockMvc.perform(get("/api/sensorEvent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.aggregateValue").isNotEmpty())
                .andReturn();
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    public void testAggregateBySensorIdAvgSuccess() throws Exception {
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("sensorId", "s1");
        paramsMap.add("groupId", "g1");
        paramsMap.add("aggregateType", AggregateType.avg.name());
        paramsMap.add("fromDate", Long.toString(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime()));
        paramsMap.add("toDate", Long.toString(Timestamp.from(Instant.now()).getTime()));

        this.mockMvc.perform(get("/api/sensorEvent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.aggregateValue").isNotEmpty())
                .andReturn();
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    public void testAggregateBySensorIdMedianSuccess() throws Exception {
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("sensorId", "s1");
        paramsMap.add("groupId", "g1");
        paramsMap.add("aggregateType", AggregateType.median.name());
        paramsMap.add("fromDate", Long.toString(Timestamp.from(Instant.parse("2022-08-20T08:44:12.250202600Z")).getTime()));
        paramsMap.add("toDate", Long.toString(Timestamp.from(Instant.now()).getTime()));

        this.mockMvc.perform(get("/api/sensorEvent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.aggregateValue").isNotEmpty())
                .andReturn();
    }

}
