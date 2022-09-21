package com.khodabandelu.iot.collector.repository;

import com.khodabandelu.iot.collector.domain.SensorEvent;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface SensorEventRepository extends CassandraRepository<SensorEvent, String> {
    List<SensorEvent> findAllById_SensorIdAndId_groupIdAndId_TimestampBetween(String sensorId,String groupId, long from, long to);
    List<SensorEvent> findAllById_SensorIdAndId_TimestampBetween(String sensorId, long from, long to);
    List<SensorEvent> findAllById_GroupIdAndId_TimestampBetween(String groupId, long from, long to);
}
