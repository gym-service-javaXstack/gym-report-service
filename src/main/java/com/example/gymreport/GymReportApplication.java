package com.example.gymreport;

import com.example.gymreport.config.FeignConfig;
import com.example.gymreport.config.JmsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({FeignConfig.class, JmsConfig.class})
public class GymReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymReportApplication.class, args);
    }

}
