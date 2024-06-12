package com.example.gymreport.service;

import com.example.gymreport.dto.ActionType;
import com.example.gymreport.dto.TrainerWorkLoadRequest;
import com.example.gymreport.model.TrainerSummary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GymReportServiceTest {

    @InjectMocks
    private GymReportService gymReportService;

    @Mock
    private TrainerSummaryService trainerSummaryService;

    @Test
    void processTrainerWorkload() {
        // Arrange
        TrainerWorkLoadRequest request = new TrainerWorkLoadRequest("username", "firstName", "lastName", true,
                LocalDate.of(2022, Month.JANUARY, 1), 60, ActionType.ADD);
        TrainerSummary trainerSummary = new TrainerSummary();
        trainerSummary.setYearlySummary(new HashMap<>());

        when(trainerSummaryService.findTrainerSummaryByTrainerWorkLoadRequest(request)).thenReturn(java.util.Optional.of(trainerSummary));

        // Act
        gymReportService.processTrainerWorkload(request);

        // Assert
        verify(trainerSummaryService).findTrainerSummaryByTrainerWorkLoadRequest(request);
        verify(trainerSummaryService).saveTrainerSummary(any(TrainerSummary.class));
    }

    @Test
    void getWorkloadByUsernameAndMonth() {
        // Arrange
        String username = "username";
        int year = 2022;
        int monthValue = 1;
        TrainerSummary trainerSummary = new TrainerSummary();
        Map<Integer, Map<Month, Integer>> yearlySummary = new HashMap<>();
        Map<Month, Integer> monthWorkload = new EnumMap<>(Month.class);
        monthWorkload.put(Month.of(monthValue), 100);
        yearlySummary.put(year, monthWorkload);
        trainerSummary.setYearlySummary(yearlySummary);

        when(trainerSummaryService.findTrainerSummaryByUsername(username)).thenReturn(java.util.Optional.of(trainerSummary));

        // Act
        Integer workload = gymReportService.getWorkloadByUsernameAndMonth(username, year, monthValue);

        verify(trainerSummaryService).findTrainerSummaryByUsername(username);
        assertThat(workload, is(100));
    }

    @Test
    void processTrainerWorkload_deleteAction() {
        // Arrange
        TrainerWorkLoadRequest request = new TrainerWorkLoadRequest("username", "firstName", "lastName", true,
                LocalDate.of(2022, Month.JANUARY, 1), 60, ActionType.DELETE);
        TrainerSummary trainerSummary = new TrainerSummary();
        Map<Integer, Map<Month, Integer>> yearlySummary = new HashMap<>();
        Map<Month, Integer> monthWorkload = new EnumMap<>(Month.class);
        monthWorkload.put(Month.JANUARY, 100);
        yearlySummary.put(2022, monthWorkload);
        trainerSummary.setYearlySummary(yearlySummary);

        when(trainerSummaryService.findTrainerSummaryByTrainerWorkLoadRequest(request)).thenReturn(java.util.Optional.of(trainerSummary));

        // Act
        gymReportService.processTrainerWorkload(request);

        // Assert
        verify(trainerSummaryService).findTrainerSummaryByTrainerWorkLoadRequest(request);
        verify(trainerSummaryService).saveTrainerSummary(trainerSummary);
        assertThat(trainerSummary.getYearlySummary().get(2022).get(Month.JANUARY), is(40));
    }

    @Test
    void processTrainerWorkload_deleteActionNegativeResult() {
        // Arrange
        TrainerWorkLoadRequest request = new TrainerWorkLoadRequest("username", "firstName", "lastName", true,
                LocalDate.of(2022, Month.JANUARY, 1), 60, ActionType.DELETE);
        TrainerSummary trainerSummary = new TrainerSummary();
        Map<Integer, Map<Month, Integer>> yearlySummary = new HashMap<>();
        Map<Month, Integer> monthWorkload = new EnumMap<>(Month.class);
        monthWorkload.put(Month.JANUARY, 50);
        yearlySummary.put(2022, monthWorkload);
        trainerSummary.setYearlySummary(yearlySummary);

        when(trainerSummaryService.findTrainerSummaryByTrainerWorkLoadRequest(request)).thenReturn(java.util.Optional.of(trainerSummary));

        // Act
        gymReportService.processTrainerWorkload(request);

        // Assert
        verify(trainerSummaryService).findTrainerSummaryByTrainerWorkLoadRequest(request);
        verify(trainerSummaryService).saveTrainerSummary(trainerSummary);
        assertThat(trainerSummary.getYearlySummary().get(2022).get(Month.JANUARY), is(0));
    }
}