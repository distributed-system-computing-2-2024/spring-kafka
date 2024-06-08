package com.teamtwo.trafficsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrafficSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrafficSystemApplication.class, args);
    }

}
