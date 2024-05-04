package com.example.gymreport.jms;

import com.example.gymreport.dto.TrainerWorkLoadRequest;
import com.example.gymreport.service.GymReportService;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TrainerWorkloadMessageHandler {
    private final GymReportService gymReportService;

    @JmsListener(destination = "trainerWorkloadQueue", selector = "_type = 'TrainerWorkLoadRequest'")
    public void handleMessage(TrainerWorkLoadRequest request, Message message) {
        try {
            String traceId = message.getStringProperty("X_Trace_Id");
            MDC.put("correlationId", traceId);

            gymReportService.processTrainerWorkload(request);
        } catch (JMSException e) {
            log.error("Error reading message property", e);
        } finally {
            MDC.remove("correlationId");
        }
    }
}