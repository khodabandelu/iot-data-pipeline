package com.khodabandelu.iot.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class IotPublisherApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotPublisherApplication.class, args);
    }

}
