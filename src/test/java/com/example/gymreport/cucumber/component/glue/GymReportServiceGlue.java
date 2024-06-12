package com.example.gymreport.cucumber.component.glue;

import com.example.gymreport.cucumber.component.CucumberComponentContext;
import com.example.gymreport.dto.ActionType;
import com.example.gymreport.dto.TrainerWorkLoadRequest;
import com.example.gymreport.model.TrainerSummary;
import com.example.gymreport.service.GymReportService;
import com.example.gymreport.testUtils.TrainerSummaryUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GymReportServiceGlue {

    @Autowired
    private GymReportService trainerWorkloadService;

    @Autowired
    private CucumberComponentContext context;

    Integer trainerWorkload;

    @Given("a trainer record with username {string} for the month {string} in {int} with duration {int}")
    public void a_trainer_record_with_username_for_the_month_in(String username, String month, Integer year, Integer duration) {
        context.setTrainerUsername(username);
        context.setWorkloadMonth(month);
        context.setWorkloadYear(year);

        TrainerSummary trainerSummaryToSave1 = TrainerSummaryUtil.getTrainerSummary1();
        TrainerSummaryUtil.addYearlySummary(trainerSummaryToSave1, year, Month.valueOf(month), duration);
        context.getMongoTrainerWorkLoadRepository().save(trainerSummaryToSave1);
    }

    @When("a request to retrieve workload by username {string} for {string} {int} is made")
    public void a_request_to_retrieve_workload_by_name_for_is_made(
            String username, String month, Integer year) {

        trainerWorkload = trainerWorkloadService.getWorkloadByUsernameAndMonth(
                username,
                year,
                Month.valueOf(month).getValue()
        );
    }

    @Then("the trainer summary in result must be {int}")
    public void the_trainer_record_with_given_username_should_be_included_in_the_response(Integer duration) {
        assertNotNull(trainerWorkload);
        assertEquals(trainerWorkload, duration);
    }

    @Given("a trainer workload request with duration {int} on the date {string} and action type {string}")
    public void a_trainer_record_with_username_and_details(Integer duration, String localDate, String actionType) {
        TrainerWorkLoadRequest trainerWorkLoadRequest = TrainerSummaryUtil.createTrainerWorkLoadRequest(LocalDate.parse(localDate), duration, ActionType.valueOf(actionType));

        context.setTrainerUsername(trainerWorkLoadRequest.getUsername());
        context.setDuration(duration);
        context.setTrainerWorkLoadRequest(trainerWorkLoadRequest);
    }

    @When("a request to process trainer workload with given trainer workload request from context")
    public void a_request_to_process_trainer_workload() {
        trainerWorkloadService.processTrainerWorkload(context.getTrainerWorkLoadRequest());
    }

    @Then("the trainer summary in result must contain {int} workload hours for the month {string}")
    public void the_trainer_summary_in_result_must_contain_workload_hours(int expectedDuration, String date) {
        LocalDate parsed = LocalDate.parse(date);

        Integer workloadByUsernameAndMonth = trainerWorkloadService.getWorkloadByUsernameAndMonth(context.getTrainerUsername(), parsed.getYear(), parsed.getMonthValue());

        assertNotNull(workloadByUsernameAndMonth);
        assertEquals(workloadByUsernameAndMonth, expectedDuration);
    }
}
