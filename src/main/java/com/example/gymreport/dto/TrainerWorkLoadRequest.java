package com.example.gymreport.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainerWorkLoadRequest {
    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private LocalDate trainingDate;
    private Long trainingDuration;
    private ActionType actionType;
}

