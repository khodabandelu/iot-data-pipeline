
## Introduction

The internet of things, or IoT, is a system of interrelated computing devices, mechanical and digital machines, objects,
animals or people that are provided with unique identifiers (UIDs) and the ability to transfer data over a network 
without requiring human-to-human or human-to-computer interaction.


## Purpose
The purpose of this project is to simulate iot sensor data and how to collect and use these data 

## Requirements
- java 17
- mosquitto --> for mqtt simulator
- kafka 
- kafka-connect --> for mqtt simulator
- cassandra
- maven 


## Modules
this project has to module 

- iot-publisher --> this module is responsible to simulate sensor data and then publish them into mqtt or kafka broker
- iot-collector --> this module is responsible to collect sensor data from kafka broker and store them into cassandra 
and then we can call some aggregate functions based on this data

## Build

- Maven 
  you can use provided maven cmd in the root of the project to build and run this project
```shell 
.\mvnw clean install
```
## Test
to test this project you can use maven test command to run provided tests in test package
```shell 
.\mvnw clean test
```

## Run
- Docker -
  you can run docker compose of docker folder to build and run this project.
```shell
docker-compose up -d
```

- ### iot-publisher module
- kafka 
to generate sensor events simulator you should call [localhost:8801/api/iot/tasks](localhost:8801/api/iot/tasks) with below configuration
```json
{
    "name":"heart rate meter job",
    "topic":"heartrate/temperature",
    "cronExpression":"*/1 * * * * *",
    "brokerType":"kafka",
    "data":
    {
        "name":"heart rate meter 10",
        "maxValue":"100",
        "minValue":"50"
    }
}
```
to generate different sensor events simulator you should call above endpoints multiple times or call  
[localhost:8801/api/iot/tasks/group](localhost:8801/api/iot/tasks/group) to generate group of sensor events simulator 
ith below configuration
```json
{
    "name":"heart rate meter job",
    "topic":"heartrate/temperature",
    "cronExpression":"*/1 * * * * *",
    "brokerType":"kafka",
    "groupId":"hearrateGroup1000",
    "data":
    [
        {
        "name":"heart rate meter 1",
        "maxValue":"80",
        "minValue":"50"
        },
         {
        "name":"heart rate meter 2",
        "maxValue":"100",
        "minValue":"70"
        }
    ]
}
```

- mqtt -> if you want to simulate mqtt events you want mqtt broker and kafka connect 
to connect kafka and mqtt broker e use kafka connect,you should add jar files mqtt connector to kafka connect and then 
call kafka rest endpoint http://localhost:8083/connectors with this below configuration 
```json
{
    "name": "mqtt-source",
    "config": {
        "connector.class": "io.confluent.connect.mqtt.MqttSourceConnector",
        "tasks.max": 1,
        "mqtt.server.uri": "tcp://mosquitto:1883",
        "mqtt.topics": "*temperature",
        "kafka.topic": "mqtt-temperature",
        "key.converter": "org.apache.kafka.connect.storage.StringConverter",
        "value.converter": "org.apache.kafka.connect.converters.ByteArrayConverter",
        "confluent.topic.bootstrap.servers": "kafka:9092",
        "confluent.topic.replication.factor": 1
    }
}
```
and then you can generate sensor events simulator with this endpoint 
[localhost:8801/api/iot/tasks](localhost:8801/api/iot/tasks) with below configuration

```json
{
    "name":"heart rate meter job",
    "topic":"heartrate/temperature",
    "cronExpression":"*/1 * * * * *",
    "brokerType":"mqtt",
    "data":
    {
        "name":"heart rate meter 10",
        "maxValue":"100",
        "minValue":"50"
    }
}
```
or generate group sensor events simulator
```json
{
    "name":"heart rate meter job",
    "topic":"heartrate/temperature",
    "cronExpression":"*/1 * * * * *",
    "brokerType":"mqtt",
    "groupId":"hearrateGroup1000",
    "data":
    [
        {
        "name":"heart rate meter 1",
        "maxValue":"80",
        "minValue":"50"
        },
        {
        "name":"heart rate meter 2",
        "maxValue":"100",
        "minValue":"70"
        }
    ]
}
```

- ### iot-collector module
after simulation ,you can collect the data from kafka and store them into cassandra then you can call query based on
these data

before you should create keyspace in cassandra
```sql
create keyspace iot_data 
    with replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

```

and you can call [localhost:8802/api/sensorEvent](localhost:8802/api/sensorEvent) with below query params 
to get aggregate value of aggregation function (average,median,max,min)
- sensorId
- groupId
- aggregateType --> avg,median,max,min
- fromDate -> time long
- toDate -> time long





