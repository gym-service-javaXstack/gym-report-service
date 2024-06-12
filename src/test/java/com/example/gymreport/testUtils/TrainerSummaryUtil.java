package com.example.gymreport.testUtils;

import com.example.gymreport.dto.ActionType;
import com.example.gymreport.dto.TrainerWorkLoadRequest;
import com.example.gymreport.model.TrainerSummary;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;

public class TrainerSummaryUtil {

    public static final String TEST_TRAINER_USERNAME_1 = "John.Trainer";
    public static final String TEST_TRAINER_FIRST_NAME_1 = "John";
    public static final String TEST_TRAINER_LAST_NAME_1 = "Trainer";
    public static final Boolean TEST_TRAINER_IS_ACTIVE_1 = true;

    public static final String TEST_TRAINER_USERNAME_2 = "Jane.Trainer";
    public static final String TEST_TRAINER_FIRST_NAME_2 = "Jane";
    public static final String TEST_TRAINER_LAST_NAME_2 = "Trainer";
    public static final Boolean TEST_TRAINER_IS_ACTIVE_2 = true;

    public static TrainerSummary getTrainerSummary1() {
        return TrainerSummary.builder()
                .username(TEST_TRAINER_USERNAME_1)
                .firstName(TEST_TRAINER_FIRST_NAME_1)
                .lastName(TEST_TRAINER_LAST_NAME_1)
                .status(TEST_TRAINER_IS_ACTIVE_1)
                .yearlySummary(new HashMap<>())
                .build();
    }

    public static TrainerSummary getTrainerSummary2() {
        return TrainerSummary.builder()
                .username(TEST_TRAINER_USERNAME_2)
                .firstName(TEST_TRAINER_FIRST_NAME_2)
                .lastName(TEST_TRAINER_LAST_NAME_2)
                .status(TEST_TRAINER_IS_ACTIVE_2)
                .yearlySummary(new HashMap<>())
                .build();
    }

    public static TrainerSummary getTrainerSummaryWithCustomValues(
            String username,
            String firstName,
            String lastName,
            Boolean status
    ) {
        return TrainerSummary.builder()
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .status(status)
                .yearlySummary(new HashMap<>())
                .build();
    }

    public static void addYearlySummary(TrainerSummary trainerSummary, Integer year, Month month, Integer hours) {
        trainerSummary.getYearlySummary().computeIfAbsent(year, y -> new HashMap<>()).put(month, hours);
    }

    public static TrainerWorkLoadRequest createTrainerWorkLoadRequest(
            LocalDate trainingDate,
            Integer duration,
            ActionType actionType) {
        return TrainerWorkLoadRequest.builder()
                .username(TEST_TRAINER_USERNAME_1)
                .firstName(TEST_TRAINER_FIRST_NAME_1)
                .lastName(TEST_TRAINER_LAST_NAME_1)
                .isActive(TEST_TRAINER_IS_ACTIVE_1)
                .trainingDate(trainingDate)
                .trainingDuration(duration)
                .actionType(actionType)
                .build();
    }
}
