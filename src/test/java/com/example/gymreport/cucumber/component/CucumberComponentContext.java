package com.example.gymreport.cucumber.component;

import com.example.gymreport.dto.TrainerWorkLoadRequest;
import com.example.gymreport.mongo.repository.MongoTrainerWorkLoadRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Month;

@Data
@Component
@Scope("cucumber-glue")
public class CucumberComponentContext {

    @Autowired
    private final MongoTrainerWorkLoadRepository mongoTrainerWorkLoadRepository;

    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private Month workloadMonth;
    private int workloadYear;
    private int duration;
    private long currentWorkingHours;

    private TrainerWorkLoadRequest trainerWorkLoadRequest;

    public void setWorkloadMonth(String month) {
        this.workloadMonth = Month.valueOf(month);
    }
}
