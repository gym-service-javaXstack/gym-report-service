package com.example.gymreport.cucumber.component;

import com.example.gymreport.GymReportApplication;
import com.example.gymreport.cucumber.config.CucumberSpringConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GymReportApplication.class)
public class CucumberComponentTestConfiguration extends CucumberSpringConfiguration {
}
