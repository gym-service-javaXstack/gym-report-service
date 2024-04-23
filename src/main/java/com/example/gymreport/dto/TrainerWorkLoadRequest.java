package com.example.gymreport.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainerWorkLoadRequest {
    private String username;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private LocalDate trainingDate;
    private Integer trainingDuration;
    private ActionType actionType;
}

