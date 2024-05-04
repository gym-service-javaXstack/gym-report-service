package com.example.gymreport.jms;

import com.example.gymreport.dto.TrainerWorkLoadRequest;
import com.example.gymreport.service.GymReportService;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "INTERACTION_BETWEEN_MICROSERVICES", havingValue = "activemq")
public class TrainerWorkloadMessageHandler {
    private static final String CORRELATION_ID_KEY = "correlationId";
    private final GymReportService gymReportService;

    @JmsListener(destination = "${application.messaging.queue.workload}", selector = "_type = 'TrainerWorkLoadRequest'")
    public void handleMessage(TrainerWorkLoadRequest request, Message message) {
        try {
            String traceId = message.getStringProperty("X_Trace_Id");
            MDC.put(CORRELATION_ID_KEY, traceId);

            gymReportService.processTrainerWorkload(request);
        } catch (JMSException e) {
            log.error("Error reading message property", e);
        } finally {
            MDC.remove(CORRELATION_ID_KEY);
        }
    }

    @JmsListener(destination = "${application.messaging.queue.trainer-summary.request}")
    @SendTo(value = "${application.messaging.queue.trainer-summary.response}")
    public Integer retrieveTrainerWorkLoadByUsername(
            @Header(value = "username") String username,
            @Header(value = "year") int year,
            @Header(value = "monthValue") int monthValue,
            @Header(value = "authHeader") String authHeader,
            @Header(value = "X_Trace_Id") String traceId
    ) {
        try {
            MDC.put(CORRELATION_ID_KEY, traceId);
            return gymReportService.getWorkloadByUsernameAndMonth(username, year, monthValue, authHeader);
        } finally {
            MDC.remove(CORRELATION_ID_KEY);
        }
    }
}