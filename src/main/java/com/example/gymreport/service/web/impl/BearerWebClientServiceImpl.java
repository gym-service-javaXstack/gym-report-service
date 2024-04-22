package com.example.gymreport.service.web.impl;

import com.example.gymreport.service.web.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class BearerWebClientServiceImpl implements WebClientService {
    private final WebClient webClient;

    @Override
    public Mono<String> requestDataFromOtherMicroservice(String authHeader) {
        return webClient.get()
                .uri("http://gym-main/api/v1/token")
                .header("Authorization", authHeader)
                .header("X-Trace-Id", MDC.get("correlationId"))
                .retrieve()
                .bodyToMono(String.class);
    }
}
