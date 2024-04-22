package com.example.gymreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class GymReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymReportApplication.class, args);
    }

}
